

import java.util.*;
import java.io.File;

public class FordFulkerson {
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph) {

		ArrayList<Integer> path = new ArrayList<Integer>();
		int[] v = new int[graph.getNbNodes()];//if value equals value store in array then visited
		pathDFS(source, destination, graph.getEdges(), v, path);
		if (path.get(path.size() - 1) != destination) {
			path = new ArrayList<Integer>();
		}
		return path;

	}


	public static void pathDFS(Integer src, Integer dest, ArrayList<Edge> e, int[] v, ArrayList<Integer> path) {
		path.add(src);
		v[src]=src;
		for (Edge ed : e) {
			if (ed.nodes[0] == src && v[ed.nodes[1]]!=ed.nodes[1] && ed.weight> 0) {
				pathDFS(ed.nodes[1], dest, e, v, path);
				if (path.get(path.size() - 1) != dest) {
					path.remove(path.size() - 1);//pop last element if not destination
				}
			}
		}
	}



	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		WGraph res= new WGraph(graph);
		Integer src= res.getSource();
		Integer dest= res.getDestination();
		for (Edge ed : graph.getEdges()){
			res.addEdge(new Edge(ed.nodes[1],ed.nodes[0], 0));
		}
		ArrayList<Integer> p=pathDFS(src, dest, res);
		while(p.size()>0){
			maxFlow+=bNaugment(res, p);
			p = pathDFS(src, dest, res);
		}
		for (Edge ed: res.getEdges() ) {
			if (graph.getEdge(ed.nodes[1], ed.nodes[0])!=null){
				graph.setEdge(ed.nodes[1], ed.nodes[0], ed.weight);//make backwards on res forwards on graph
			}
		}
		answer +=  maxFlow + "\n" + graph.toString();
		return answer;
	}




	private static int bNaugment(WGraph res,  ArrayList<Integer> path){
		int beta=700000;
		int i=1;
		while(i<path.size()){
			Edge ed= res.getEdge(path.get(i-1), path.get(i));
			if(ed!=null) beta=Math.min(beta, ed.weight);
			i++;
		}
		i=1;
		while(i<path.size()){
			Edge ed= res.getEdge(path.get(i-1), path.get(i));
			if(ed!=null) {
				ed.weight -= beta;
				res.getEdge(ed.nodes[1], ed.nodes[0]).weight += beta;
			}
			i++;
		}
		return beta;
	}





	 public static void main(String[] args){
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));
	 }
}

