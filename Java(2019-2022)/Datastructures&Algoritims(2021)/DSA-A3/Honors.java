import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {
// 	private static class Field{
	
// 		private int x;
// 		private int y;

// //		private ArrayList<Field> adj;//useless because setup was slow(shouldnt iterate through all fields, just the one needed at the time)
// 		private int v;
// 		private int distance;
// 		private boolean color;//useless just use array

// 		/*1. white
// 		 *2. gray
// 		 *3. black
// 		*/
		
		
// 		private Field(int x, int y, int val, int d, boolean c) {
		
// 			this.x=x;
// 			this.y=y;

// 			this.v=val;
// 			this.distance=d;
// 			this.color=c;



// 		}
// 		public void cGray(){
// 			this.color=true;
// 		}


// 		public int value() {
// 			return this.v;
// 		}
// //		//public ArrayList<Field> getAdj() {
// //			return this.adj;
// //		}
		
// //		public void addAdj(Field x) {
// //			this.adj.add(x);
// //			return;
// //		}
// 		public int getDistance(){
// 			return this.distance;
// 		}
		
// 	}

//	private static void setup(int[][] board, Field[][] a){
//		int value;
//		for(int i=0; i<board.length; i++) {//private Field( int x, int y, int sizeX, int sizeY,int val, int d, int c) {
//			for(int j=0; j<board[0].length; j++) {
//				value= board[i][j];
//				a[i][j] = new Field(i, j,  value, 0, 1);
//			}
//		}
//		a[0][0].setToGray();//first node is colored gray
//
//

//		for(Field t: f) {//slow
//			if(t.y+t.v<=maxY) {
//				tmp=t.y+t.v;
//				t.addAdj(a[t.x][tmp]);
//			}
//			if(t.x+t.v<=maxX) {//private Field( x, y, val on tile,  distance,  color[1.white, 2. gray, 3. black]) {
//				tmp=t.x+t.v;
//				t.addAdj(a[tmp][t.y]);
//			}
//			if(t.x-t.v>=0) {
//				tmp=t.x-t.v;
//				t.addAdj(a[tmp][t.y]);
//			}
//
//			if(t.y-t.v>=0) {
//				tmp=t.y-t.v;
//				t.addAdj(a[t.x][tmp]);
//			}
//		}
//	}
	private static class Tuple{
		private int x;
		private int y;
		private Tuple(int x, int y){
			this.x=x;
			this.y=y;
		}
	}

	
	public static int min_moves(int[][] board) {


		boolean b[][]= new boolean[board.length][board[0].length];
		int distance[][]=new int[board.length][board[0].length];//distanceGraph


		LinkedList<Tuple> q = new LinkedList<>();


		Tuple u= new Tuple(0,0);
		b[0][0]=true;

		q.add(u);

		while(q.size()>0) {

			u=q.poll();
			int val=board[u.x][u.y];

			Tuple v;
			if(u.y+val<=board[0].length-1) {
				if(!b[u.x][u.y+val]){
					v=new Tuple (u.x,u.y+val);
					b[v.x][v.y]=true;
					distance[v.x][v.y]=distance[u.x][u.y] +1;
					if (v.x == board.length - 1 && v.y== board[0].length - 1) {
						return distance[v.x][v.y];
					}
					q.add(v);

				}
			}
			if(u.x+val<=board.length-1) {
				if(!b[u.x+val][u.y]){
					v=new Tuple (u.x+val,u.y);
					b[v.x][v.y]=true;
					distance[v.x][v.y]=distance[u.x][u.y] +1;
					if (v.x == board.length - 1 && v.y== board[0].length - 1) {
						return distance[v.x][v.y];
					}
					q.add(v);

				}
			}
			if(u.x-val>=0) {
				if(!b[u.x-val][u.y]){
					v=new Tuple (u.x-val,u.y);
					b[v.x][v.y]=true;
					distance[v.x][v.y]=distance[u.x][u.y] +1;
					if (v.x == board.length - 1 && v.y== board[0].length - 1) {
						return distance[v.x][v.y];
					}
					q.add(v);

				}
			}
			if(u.y-val>=0) {
				if(!b[u.x][u.y-val]){
					v=new Tuple (u.x,u.y-val);
					b[v.x][v.y]=true;
					distance[v.x][v.y]=distance[u.x][u.y] +1;
					if (v.x == board.length - 1 && v.y== board[0].length - 1) {
						return distance[v.x][v.y];
					}
					q.add(v);

				}
			}
			// if(u.x+val<=board[0].length-1) {
			// 	if(!b[u.x+val][u.y]){
			// 		b[u.x+val][u.y]=true;
			// 		distance[u.x+val][u.y]=distance[u.x][u.y] +1;
			// 		if (u.x == board.length - 1 && u.y+val== board[0].length - 1) {
			// 			return distance[u.x][u.y+val];
			// 		}
			// 		Tuple v=new Tuple (u.x+val,u.y);
			// 		q.add(v);
			// 	}
			// }


			// if(u.x+val<=board.length-1) {//private Field( x, y, val on tile,  distance,  color[1.white, 2. gray, 3. black]) {
			// 	v=a[u.x+u.v][u.y];
			// 	if(!v.color){
			// 		v.color=true;
			// 		v.distance = u.distance + 1;
			// 		q.add(v);
			// 		if (v.x == board.length - 1 && v.y == board[0].length - 1) {
			// 			return v.distance;
			// 		}
			// 	}
			// }
			// if(u.x-val>=0) {
			// 	v=a[u.x-u.v][u.y];
			// 	if (!v.color) {
			// 		v.color=true;
			// 		v.distance = u.distance + 1;
			// 		q.add(v);
			// 		if (v.x == board.length - 1 && v.y == board[0].length - 1) {
			// 			return v.distance;
			// 		}
			// 	}
			// }

			// if(u.y-val>=0) {
			// 	v=a[u.x][u.y-u.v];
			// 	if (!v.color) {
			// 		v.color=true;
			// 		v.distance = u.distance + 1;
			// 		q.add(v);
			// 		if (v.x == board.length - 1 && v.y == board[0].length - 1) {
			// 			return v.distance;
			// 		}
			// 	}
			// }

			b[u.x][u.y]=true;
		}
		return -1;



	}

	public void test(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int [][]board = new int[num_rows][num_columns];
		for (int i=0; i<num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j=0; j<num_columns; j++) {
				board[i][j] = (int)(line[j]-'0');
			}
			
		}
		sc.close();
		int answer = min_moves(board);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Honors honors = new Honors();
		honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'
		
		// or you can test like this
		// int [][]board = {1,2,3}; // not actual example
		// int answer = min_moves(board); 
		// System.out.println(answer);
	}
}
