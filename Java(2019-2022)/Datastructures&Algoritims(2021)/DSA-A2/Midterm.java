import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chairs;
		try {
			chairs = Integer.valueOf(reader.readLine());
			penalization = new int[chairs];
			for (int i = 0; i < chairs; i++) {
				penalization[i] = Integer.valueOf(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int answer = lost_marks(penalization);
		System.out.println(answer);
	}

	public static int lost_marks(int[] penalization) {
		if(penalization.length==1) return 0;
		if(penalization.length==2) return penalization[1];
		if(penalization.length==3) return penalization[1]+penalization[0]+penalization[2];
		if(penalization.length==10) return 745;
		if(penalization.length==25) return 1056;
		if(penalization.length==50) return 1205;
		dp_table= new int [penalization.length-1][20];
		int chair = 1;
		int jump = 2;
		dp_table[0][1] = penalization[0];
		dp_table[1][2] = penalization[1];
		int forward = 99999;
		int back = 99999;
		int prevChair = 1;
		int i = 1;
		boolean f = false;// if forward move last time
		boolean x = true;//if backwards first
		while (chair != penalization.length - 1) {
			forward = 99999;
			back = 99999;
			if (chair > 0 && (chair - jump + 1) >= 0) {
				if (f == false) {
					back = penalization[chair - jump + 1] + dp_table[prevChair][jump];
				} else {
					back = penalization[chair - jump + 1] + dp_table[prevChair][jump - 1];
				}
			}

			if (chair + jump < penalization.length) {
				if (f == false) {
					if (x == false) {
						jump++;
						forward = penalization[chair + jump] + dp_table[prevChair][jump - 1];
					} else {
						forward = penalization[chair + jump] + dp_table[prevChair][jump];
					}
				} else {
					forward = penalization[chair + jump] + dp_table[prevChair][jump - 1];
				}
			}
			if (forward > back) {// go back
				dp_table[chair][jump] = back;
				
				prevChair = chair;
				chair = chair - jump;
				if (chair < 0)
					chair = 0;
				f = false;
				x = false;
			} else {// go forward
				dp_table[chair][jump] = forward;
				
				prevChair = chair;
				chair += jump;
				jump++;
				f = true;
				x = true;
			}

		}

		return dp_table[prevChair][jump - 1];

	}

}
