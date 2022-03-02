package finalproject;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
 
public class Sorting {
 
 
 
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls
 
        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
	private static <V extends Comparable> void merges(Object[]  d, Object[] one, Object[] two, Object[] keys, Object[] k1, Object[] k2, int bot) {//adapted from the slides. I had a few errors so I fixed them via my notes from Sasha's tutorial. Also, it's an algorithim. So there are only a few ways to do it.
		int b=0;
		int a=b;
		int k=bot;// k relates to d because DK- donkey kong put your hands together
 
 
 
		while(b<two.length && a<one.length){
			d[k++]=((V)two[b]).compareTo((V)one[a])>=0? two[b++]: one[a++];
		}
 
		//remaining elements (because of the and statement in the while loop above one side will run out of elements and the other side could still have elements)
		for(a=a; a<one.length;a++){
			d[k]=one[a];
			keys[k++]=k1[a];
		}
		for(b=b; b<two.length;b++){
			d[k]=two[b];
			keys[k++]=k2[b];
		}
	}
 
 
 
	private static void mergeSort(Object[] all, Object keys[],  int bottom, int top){
		if(bottom>=top) {
			return;
		}
 
		int half=(bottom+top)/2+1;
 
		Object one[] = new Object[half-bottom];
		Object two[] = new Object[top+1-half];
		Object oneK[] = new Object[half-bottom];
		Object twoK[] = new Object[top+1-half];
 
		int a=0;
		int b=0;
 
		mergeSort(all, keys, bottom,half-1);
		mergeSort(all, keys, half, top);
 
		for (a = 0; a <= half-bottom-1; a++) {
			one[a] = all[bottom + a];
			oneK[a] = all[bottom + a];
		}
 
		for (b = 0; b < top+1-half; b++) {
			two[b] = all[half + b];
			twoK[b] = all[half + b];
		}
 
		merges(all,one, two, keys, oneK, twoK, bottom);
 
	}
 
 
 
 
	public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
		int s=results.values().size();
		Object[] res = new Object[s];
		Object[] key = new  Object[s];
		res =  results.values().toArray(res);
		key = results.keySet().toArray(res);
		mergeSort(res, key, 0,s-1);
		ArrayList<K> sortedUrls =  new ArrayList<K>();
 
		for(Object k: key){
			sortedUrls.add((K)k);
		}
 
		return sortedUrls;
    }
 
 
 
 
 
}