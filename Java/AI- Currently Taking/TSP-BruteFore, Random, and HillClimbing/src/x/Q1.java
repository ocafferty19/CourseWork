package x;

import java.util.*;

public class Q1 {
	public static void main(String args[]) {

		Random x = new Random();
		x.setSeed((long) 25);
		int rOpt = 0;
		int hCOpt = 0;

		double[] hCCost = new double[100];
		double[] rCost = new double[100];
		double[] bFCost = new double[100];

		int rOptD = 0;
		int hCOptD = 0;

		double[] hCCostD = new double[100];
		double[] rCostD = new double[100];
		double[] bFCostD = new double[100];
		for (int i = 0; i < 100; i++) {

			City[] r = randomTour(7, x);
			//System.out.println(i);
			City[] rBF = r;
			City[] rHC = r;
			City[] bF = bruteForce(rBF);
	

			City[] hC = hillClimb(rHC);

			double optimalSolutionCost = tourDistance(bF);
	
			double randomOptimalSolution = tourDistance(r);
			//System.out.println(i);
			double hCOptimalSolution = tourDistance(hC);
			//System.out.println(i);
			if (randomOptimalSolution==optimalSolutionCost ) {
				rOpt++;
			}
			if (hCOptimalSolution ==optimalSolutionCost) {
				hCOpt++;
			}
			hCCost[i] = hCOptimalSolution;
			rCost[i] = randomOptimalSolution;
			bFCost[i] = optimalSolutionCost;
//			
//			
//			City[] rD = randomTour(100, x);
//			//System.out.println(i);
//			City[] rBFD = rD;
//			City[] rHCD = rD;
//			City[] bFD = bruteForce(rBFD);
//	
//
//			City[] hCD = hillClimb(rHCD);
//
//			double optimalSolutionCostD = tourDistance(bFD);
//	
//			double randomOptimalSolutionD = tourDistance(bFD);
//			//System.out.println(i);
//			double hCOptimalSolutionD = tourDistance(hCD);
//			System.out.println(i);
//			if (randomOptimalSolutionD==optimalSolutionCostD ) {
//				rOptD++;
//			}
//			if (hCOptimalSolutionD ==optimalSolutionCostD) {
//				hCOptD++;
//			}
//			hCCostD[i] = hCOptimalSolutionD;
//			rCostD[i] = randomOptimalSolutionD;
//			bFCostD[i] = optimalSolutionCostD;


//
			//System.out.println(i);
		}
		System.out.println("A.");
		double bFMean=mmm(bFCost);
		sD(bFCost,bFMean);

		// sd(bFCost);
		System.out.println("B.");
		double rMean=mmm(rCost);
		sD(rCost,rMean);
		System.out.println("Num of optimal solutions: " + rOpt);
		System.out.println("C.");
		double hCMean=mmm(hCCost);
		sD(hCCost,hCMean);
		System.out.println("Num of optimal solutions: " + hCOpt);
		System.out.println("D.");
		double rMeanD=mmm(rCostD);
		sD(rCostD,rMeanD);
		System.out.println("Random num of optimal solutions: " + rOptD);
		double hCMeanD=mmm(hCCostD);
		sD(hCCostD,hCMeanD);
		System.out.println("Hill CLimbing num of optimal solutions: " + hCOptD);
		

	}

	public static double mmm(double[] nums) {
		double sum = 0;
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < 100; i++) {
			if (nums[i] > max) {
				max = nums[i];
			}
			if (nums[i] < min) {
				min = nums[i];
			}
			sum += nums[i];
		}
		double mean=sum / nums.length;
		System.out.println("Mean: " + mean);
		System.out.println("Minimum: " + min);
		System.out.println("Max: " + max);
		return mean;

	}
	public static void sD(double[] nums, double mean) {
		double sum = 0;
		
		for (int i = 0; i < nums.length; i++) {
			sum+=Math.pow(nums[i]-mean,2);
		}
		double inside= sum/nums.length;
		double sd=Math.sqrt(inside);
		System.out.println("Standard Deviation: " + sd);
		

	}
	

	public static double euclidian_Distance(City city1, City city2) {
		/*
		 * double x1=city1.x; double x2=city2.y; double y1=city1.x; double y2=city2.y;
		 */
		double diffy = Math.pow(city2.y - city1.y, 2);
		double diffx = Math.pow(city2.x - city1.x, 2);
		double eD = Math.sqrt(diffx + diffy);
		return eD;
	}

	public static City[] randomTour(int size, Random var) {

		City[] c = new City[size];
		for (int i = 0; i < size; i++) {
			double rx = var.nextDouble();

			double ry = var.nextDouble();
			c[i] = new City(rx, ry);

		}
		// System.out.println(tourDistance(c));
		return c;
	}

	// 1->2-> 5-> 4-> 7-> 6-> 3->
	// 3-> 1-> 2 ->5 4-> 7-> 6->
	// 1->2->5->6->7->4->3->
	// (1->2->5) ->(6->7->4) -> 3->
	// 4->7->6 is inverted
	public static City[] twoChange(City[] X, int i, int ind) {

		City[] n = Arrays.copyOf(X, X.length);
		City[] Y = Arrays.copyOf(X, X.length);
		for (int j = 0; j < i; j++) {
			n[j] = Y[j];
			// System.out.println(n[j]);
			// System.out.println(X[j]);

		}

		int z = ind;
		for (int j = i; j <= ind; j++) {

			n[j] = Y[z];

			z--;
		}
		for (int j = ind + 1; j < X.length; j++) {
			n[j] = Y[j];

		}
		return n;
	}

	public static double tourDistance(City[] X) {
		double sum = 0;
		for (int i = 0; i < X.length - 1; i++) {
			sum += euclidian_Distance(X[i], X[i + 1]);
		}
		sum += euclidian_Distance(X[0], X[X.length - 1]);
		return sum;

	}

	public static City[] bruteForce(City[] X0) {// implemented using heap's algo
												// https://www.baeldung.com/java-array-permutations to develop all the
												// permutations of the array(tour)

		City[] tour = Arrays.copyOf(X0, X0.length);
		City[] optimal = Arrays.copyOf(X0, X0.length);

		int[] indexes = new int[X0.length];

		for (int i = 0; i < X0.length; i++) {
			indexes[i] = 0;
		}
		int i = 0;
		while (i < X0.length) {
			if (indexes[i] < i) {

				swap(tour, i % 2 == 0 ? 0 : indexes[i], i);
				if (tourDistance(optimal) - tourDistance(tour) > .0000000000000000001) {

					optimal = Arrays.copyOf(tour, tour.length);

				}
				indexes[i]++;
				i = 0;

			} else {
				indexes[i] = 0;
				i++;
			}
		}

		return optimal;
	}

	private static void swap(City[] input, int a, int b) {
		City tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
	}

	public static City[] hillClimb(City[] X0) {

		City[] Xstar = Arrays.copyOf(X0, X0.length);
		City[] X = Arrays.copyOf(X0, X0.length);

		

	
		double EX0 = tourDistance(X0);
		double E = EX0;
		double EMin = EX0;

		while (true) {

			for (int i = 0; i < X0.length; i++) {//n chose 2 taken from https://stackoverflow.com/questions/38174630/most-efficient-way-to-find-all-combinations-of-n-choose-2
				for (int ind =i+1; ind < X0.length; ind++) {
					

					City[] temp =Arrays.copyOf(twoChange(Arrays.copyOf(Xstar, Xstar.length), i, ind), Xstar.length);

					double Ei = tourDistance(temp);
					// System.out.println(Ei);
					if (Math.min(Ei, EMin) != EMin) {
						// System.out.println(Ei+ " " +EMin);
						EMin = Ei;
						
						Xstar = Arrays.copyOf(temp, temp.length);
						
					}
					ind++;
				}
			}
			if (EMin >=E) {
				return X;
			} else {
				E = EMin;
				X = Arrays.copyOf(Xstar, Xstar.length);
			}

		}
	}
}
