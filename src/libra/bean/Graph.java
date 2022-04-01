package libra.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class Graph{
	HashMap<String, HashSet<String>> adj = new HashMap<String, HashSet<String>>();
	ArrayList<ArrayList<String>> paths = new ArrayList<ArrayList<String>>();
	
	public Graph() {
		
	}
	
	public void find_all_paths(String src, String dst) {
		ArrayList<ArrayList<String>> tmp_paths = new ArrayList<ArrayList<String>>();
		tmp_paths.add(new ArrayList<String>());
		tmp_paths.get(0).add(src);
		int index = 0;
		while(tmp_paths.size() != index) {
			ArrayList<String> path_now = tmp_paths.get(index);
			String path_end = path_now.get(path_now.size()-1);
			if(path_end.equals(dst)) {
				this.paths.add(path_now);
			}
			if(adj.containsKey(path_end)) {
				for(String adj_node : adj.get(path_end)) {
					ArrayList<String> path_new = new ArrayList<String>();
					path_new.addAll(path_now);
					path_new.add(adj_node);
					tmp_paths.add(path_new);
				}
			}
			index++;
		}
	}
	
	public void addEdge(String src, String dst) {
		if(adj.containsKey(src)) {
			adj.get(src).add(dst);
		}else {
			adj.put(src, new HashSet<String>());
			adj.get(src).add(dst);
		}
	}
}