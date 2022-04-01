package libra.bean;

import com.google.javascript.jscomp.graph.DiGraph;
import com.google.javascript.jscomp.graph.LinkedDirectedGraph;

import java.util.ArrayList;
import java.util.HashSet;

import bean.Network;
import bean.basis.BasicTF;
import bean.basis.Node;
import bean.basis.Rule;

public class GraphVerifier{
	public static ArrayList<Node> findReachability(Network network, Node inputPkt, ArrayList<Integer> outPorts){
		return findReachability(network.getNTF(),network.getTTF(),inputPkt,outPorts);
	}
	
	public static ArrayList<Node> findReachability(BasicTF BasicNTF, BasicTF BasicTTF, Node inputPkt, ArrayList<Integer> outPorts){
		ArrayList<Node> result = new ArrayList<Node>();
		Graph g = new Graph();
		HashSet<Integer> allPorts = new HashSet<Integer>();
		HashSet<Integer> edgePorts = new HashSet<Integer>();
		for(int port : BasicNTF.outportToRule.keySet()) {
			allPorts.add(port);
		}
		for(int port : BasicNTF.inportToRule.keySet()) {
			allPorts.add(port);
		}
		for(int port : BasicTTF.outportToRule.keySet()) {
			allPorts.add(port);
		}
		for(int port : allPorts) {
			if(!BasicTTF.outportToRule.containsKey(port)) {
				edgePorts.add(port);
			}
		}
		for(int port : BasicNTF.inportToRule.keySet()) {
			ArrayList<Rule> rules = BasicNTF.inportToRule.get(port);
			for(Rule rule : rules) {
				if(!inputPkt.getHdr().copyAnd(rule.getMatch()).isEmpty()) {
					for(int inport : rule.getInPorts()) {
						for(int outport : rule.getOutPorts()) {
							g.addEdge(inport+"_in", outport+"_out");
						}
					}
				}
			}
		}
		for(int port : BasicTTF.inportToRule.keySet()) {
			ArrayList<Rule> rules = BasicTTF.inportToRule.get(port);
			for(Rule rule : rules) {
				for(int inport : rule.getInPorts()) {
					for(int outport : rule.getOutPorts()) {
						g.addEdge(inport+"_out", outport+"_in");
					}
				}
			}
		}
		ArrayList<ArrayList<String>> paths = new ArrayList<ArrayList<String>>();
		for(int outPort : outPorts) {
			g.find_all_paths(inputPkt.getPort()+"_in", outPort+"_out");
			paths.addAll(g.paths);
		}
		for(ArrayList<String> path : paths) {
			Node node = new Node();
			for(int i = 0; i < path.size(); i++) {
				String port = path.get(i);
				int port_num = Integer.parseInt(port.split("_")[0]);
				if(i == (path.size() - 1)) {
					node.setPort(port_num);
					node.setHdr(inputPkt.getHdr().copy());
				}else {
					node.getVisits().add(port_num);
					node.getHsHistory().add(inputPkt.getHdr().copy());
				}
			}
			result.add(node);
		}
		return result;
	}
}