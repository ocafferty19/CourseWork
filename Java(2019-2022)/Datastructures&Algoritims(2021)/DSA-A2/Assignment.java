import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 */
	@Override
	public int compare (Assignment o, Assignment t) {
		if(o.weight==t.weight) {
			if(o.deadline==t.deadline) {
				return 0;
			} else if(o.deadline<t.deadline) {
				return 1;
			} else {
				return -1;
			}
		} else if(o.weight<t.weight) {
			return 1;
		}  else {
			return -1;
		}
	}
}
