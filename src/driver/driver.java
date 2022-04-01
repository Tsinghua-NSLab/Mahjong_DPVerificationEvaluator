package driver;

import java.util.ArrayList;

import utils.Save;
import utils.Gephi;
import bean.Network;
import bean.basis.Node;
import factory.HeaderFactory;
import factory.TransferFuncFactory;
import apverifier.bean.APVTransFunc;

public class driver{
	Network network = new Network();	
	
	public static void main(String args[]) {
		Network network = new Network();
		network.importFromFile("examples\\2_4_4.network");//input for fattree 244
		//network.importFromFile("examples\\4_8_16.network");//input for fattree 488
		//network.importFromFile("examples\\simple_stanford.network");//input for stanford backbone
		utils.Gephi.network2gml(network);
		Node Pkt = new Node();
		//32 bit for StanfordSimple, 34 bit for fattrees
		//Pkt.setHdr(HeaderFactory.generateInputHeader(34,'x'));
		//Pkt.setHdr(HeaderFactory.generateHeader(APVTransFunc.predicates.size()));
		Pkt.setHdr(HeaderFactory.generateInputHeader("xxxxxxxxxxxxxxxxxxxxxxxxxx11111110"));
		
		Pkt.setPort(100);//inport for fattree 244
		//Pkt.setPort(10101);//inport for fattree 4816
		//Pkt.setPort(300001);//inport for stanford backbone
		ArrayList<Integer> Ports = new ArrayList<Integer>();
		Ports.add(133);//out port for fattree 244
		//Ports.add(11503);//out port for fattree 4816
		//Ports.add(1600004);//out port for stanford backbone
		ArrayList<Node> result = TransferFuncFactory.findReachabilityByPropagation(network.getNTF(), network.getTTF(), Pkt, Ports);
		System.out.println("Path num: " + result.size());
		System.out.println(result);
		utils.Gephi.path2gml(network, result);
	}
}