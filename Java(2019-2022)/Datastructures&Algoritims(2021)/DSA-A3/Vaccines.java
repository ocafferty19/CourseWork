import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Vaccines {

	public class Country {
		private int ID;
		private int vaccine_threshold;
		private int vaccine_to_receive;

		private boolean marked;


		private ArrayList<Integer> allies_ID;
		private ArrayList<Integer> allies_vaccine;

		public Country() {
			this.allies_ID = new ArrayList<Integer>();
			this.allies_vaccine = new ArrayList<Integer>();
			this.vaccine_threshold = 0;//amt needed
			this.vaccine_to_receive = 0;//amt from countries
			this.marked = false;
		}

		public int get_ID() {
			return this.ID;
		}

		public int get_vaccine_threshold() {
			return this.vaccine_threshold;
		}

		public ArrayList<Integer> get_all_allies_ID() {
			return allies_ID;
		}

		public ArrayList<Integer> get_all_allies_vaccine() {
			return allies_vaccine;
		}

		public int get_allies_ID(int index) {
			return allies_ID.get(index);
		}

		public int get_allies_vaccine(int index) {
			return allies_vaccine.get(index);
		}

		public int get_num_allies() {
			return allies_ID.size();
		}

		public int get_vaccines_to_receive() {
			return vaccine_to_receive;
		}

		public void set_allies_ID(int value) {
			allies_ID.add(value);
		}

		public void set_allies_vaccine(int value) {
			allies_vaccine.add(value);
		}

		public void set_ID(int value) {
			this.ID = value;
		}

		public void set_vaccine_threshold(int value) {
			this.vaccine_threshold = value;
		}

		public void set_vaccines_to_receive(int value) {
			this.vaccine_to_receive = value;
		}

		public void setMarked() {
			this.marked = true;
		}
	}

//	public int vaccines(Country[] graph) {
//		Stack<Country> s = new Stack<Country>();
//		s.push(graph[0]);
//		int numC = 0;
////i=0 then ID=1 so i=ID-1
//		int f = 0;
//		while (s.size() != 0) {
//			Country v = s.pop();
//			if (!v.marked) {
//				//always on locked down
//				if (f == 0) {
//					f++;
//					for (int i : v.get_all_allies_ID()) {
//						if (!graph[i-1].marked) {
//							s.push(graph[i-1]);
//							break;
//						}
//					}
//					v.setMarked();
//				} else {
//
//
//
//					for (int i : v.get_all_allies_ID()) {
//						if (!graph[i-1].marked) {
//							v.vaccine_to_receive += graph[i].vaccine_threshold;
//							s.push(graph[i-1]);
//							break;
//						}
//					}
//
//					if (v.vaccine_threshold > v.vaccine_to_receive) {
//						v.setMarked();
//					} else{
//						numC++;
//					}
//				}
//			}
//		}
//
//		//Computing the exceed in vaccines per country.
//		return numC;
//	}
	public static int vaccinesHelper(Country[] graph, Country v) {
		int numC = 0;
		if (!v.marked) {
			//handle cases and compute num(i and iterate via i++ when country is good)
			v.setMarked();//needs to be at top because
			for (int z : v.get_all_allies_ID()) {
				if (!graph[z - 1].marked) {
						vaccinesHelper(graph, graph[z - 1]);

				}
			}
		}
		return numC;
	}




	public int vaccines(Country[] graph) {
		int sum=0;

		//sum=vaccinesHelper(graph, graph[0]);//handle case one and get all neighboring countries and then recursively iterate through rest
		//System.out.println(graph[0].vaccine_to_receive);
		//System.out.println(graph[0].vaccine_threshold);
		return sum;
	}


	public void test(String filename) throws FileNotFoundException {
		Vaccines hern = new Vaccines();
		Scanner sc = new Scanner(new File(filename));
		int num_countries = sc.nextInt();
		Country[] graph = new Country[num_countries];
		for (int i=0; i<num_countries; i++) {
			graph[i] = hern.new Country(); 
		}
		for (int i=0; i<num_countries; i++) {
			if (!sc.hasNext()) {
                sc.close();
                sc = new Scanner(new File(filename + ".2"));
            }
			int amount_vaccine = sc.nextInt();
			graph[i].set_ID(i+1);
			graph[i].set_vaccine_threshold(amount_vaccine);
			int other_countries = sc.nextInt();
			for (int j =0; j<other_countries; j++) {
				int neighbor = sc.nextInt();
				int vaccine = sc.nextInt();
				graph[neighbor -1].set_allies_ID(i+1);
				graph[neighbor -1].set_allies_vaccine(vaccine);
				graph[i].set_vaccines_to_receive(graph[i].get_vaccines_to_receive() + vaccine);
			}
		}
		sc.close();
		int answer = vaccines(graph);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Vaccines vaccines = new Vaccines();
		vaccines.test(args[0]); // run 'javac Vaccines.java' to compile, then run 'java Vaccines testfilename'
	}

}
