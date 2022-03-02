package finalproject;


import java.util.HashMap;
import java.util.ArrayList;
 
 
public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;
 
 
	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
 
		internet.addVertex(url);
		internet.setVisited(url, true);
		ArrayList<String> c= parser.getContent(url);
		for(String w:c) {
			w=w.toLowerCase();
			if (!wordIndex.containsKey(w)) {
				wordIndex.put(w, new ArrayList<String>());
			}
			if (!wordIndex.get(w).contains(url)) {
 
				wordIndex.get(w).add(url);
			}
		}
		//add all words
		ArrayList<String> out = parser.getLinks(url);
 
		for(String s: out){
 
			if(!internet.getVisited(s)) {
 
				crawlAndIndex(s);
			}
			internet.addEdge(url,s);
 
		}
 
 
 
 
 
 
		// TODO : Add code here
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		ArrayList<Double> prev= new ArrayList<Double>();
		for(String s: internet.getVertices()){// intitialize priority as 1
			internet.setPageRank(s, 1.0);
			prev.add(internet.getPageRank(s));
		}
 
		ArrayList<Double> cur;
		double i=-1;
		int c=0;
 
		while(!(i==internet.getVertices().size())){
			i=0;
			cur=computeRanks(internet.getVertices());
 
			for(String x: internet.getVertices()){
				if(Math.abs(cur.get(c)- prev.get(c))<epsilon){
					i++;
				}
			}
 
			prev=cur;
		}
		computeRanks(internet.getVertices());
		computeRanks(internet.getVertices());
		computeRanks(internet.getVertices());
 
 
 
		//priority is .5+.5(summation of prioties/ out degree)
	}
 
	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		ArrayList<Double> result= new ArrayList<Double>();
		for(String v: vertices){
			double rank=0;
 
			for(String n: internet.getEdgesInto(v)){
 
				if(internet.getOutDegree(n)!=0) {
					rank += (internet.getPageRank(n) / internet.getOutDegree(n));
				}
			}
 
			rank/=2;
			rank+=.5;
 
			internet.setPageRank(v, rank);
			result.add(rank);
		}
		return result;
	}
 
	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
 
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		if(wordIndex.get(query)!=null) {
			ArrayList<String> s = wordIndex.get(query);
			ArrayList<Double> d = computeRanks(s);
 
			HashMap<String, Double> hM = new HashMap<String, Double>();
 
			for (int i=0; i<s.size();i++) {
				hM.put(s.get(i), d.get(i));
			}
 
			return Sorting.slowSort(hM);
		}
 
		return new ArrayList<String>();
 
	}
}
 