
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class a1_real {
	
	public static int silence(int[] positions) {
		int mD=positions.length;//min distance
		
		HashMap<Integer, Integer> h=new HashMap<>(mD);
		
		int p=0;//prev time integer was found
		int i=0;//index @
		while(i<positions.length){
			p=h.getOrDefault(positions[i],-1);
			if(p!=-1) mD=(mD<i-p)? mD:i-p;
			h.put(positions[i],i++);
		}
		return mD;
	}
		

	
			

	public static void main(String[] args) {
		try {
			String path = args[0];
      		File myFile = new File(path);
      		Scanner sc = new Scanner(myFile);
      		int num_students = sc.nextInt();
      		int[] positions = new int[num_students];
      		for (int student = 0; student<num_students; student++){
				positions[student] =sc.nextInt();
      		}
      		sc.close();
      		int answer = silence(positions);
      		System.out.println(answer);
    	} catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}
  	}		
}