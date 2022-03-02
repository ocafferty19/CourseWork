import java.util.*;

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;

	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i = 0; i < size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m = size;
	}

	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment that will be done
	 *         at time i.
	 */

	public int[] SelectAssignments() {
		// TODO Implement this

		// Sort assignments
		// Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());

		// If homeworkPlan[i] has a value -1, it indicates that the
		// i'th timeslot in the homeworkPlan is empty
		// homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i = 0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}
		int d;
		int i;
		for (Assignment a : Assignments) {
			d = a.deadline - 1;
			i = 0;
			if (homeworkPlan[d] == -1) {
				homeworkPlan[d] = a.number;
			} else {
				while (i <= d) {
					if (homeworkPlan[i] == -1) {
						homeworkPlan[i] = a.number;
					}
					i++;
				}
			}
		}

		return homeworkPlan;
	}
}
