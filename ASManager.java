package ASUtils;

import java.util.*;

public class ASManager implements ASManageable {

	public ASManager() {

		dataSet = new TreeSet<>();
		usedPriority = new HashSet<>();
		highPriority = 0;
		lowPriority = 0;
	}

	@Override
	public void manage() {
		for (ManagerData md : dataSet) {
			md.obj.manage();
		}
	}

	public void addHighPriority(ASManageable m) {
		if (usedPriority.contains(highPriority + 1)) {
			dataSet.add(new ManagerData(m, highPriority + 2));
			highPriority += 2;
		} else {
			dataSet.add(new ManagerData(m, highPriority + 1));
			highPriority += 1;
		}

		usedPriority.add(highPriority);
	}

	public void addLowPriority(ASManageable m) {
		if (usedPriority.contains(lowPriority - 1)) {
			dataSet.add(new ManagerData(m, lowPriority - 2));
			lowPriority -= 2;
		} else {
			dataSet.add(new ManagerData(m, lowPriority - 1));
			lowPriority -= 1;
		}

		usedPriority.add(lowPriority);
	}

	public void addWithPriority(ASManageable m, int priority) {
		if (!usedPriority.contains(priority)) {
			dataSet.add(new ManagerData(m, priority));
			usedPriority.add(priority);
		}
	}

	public void remove(ASManageable m) {
		for (ManagerData md : dataSet) {
			if (md.obj == m) {
				dataSet.remove(md);
				return;
			}
		}
	}

	public void makeHighPriority(ASManageable m) {
		for (ManagerData md : dataSet) {
			if (md.obj == m) {
				dataSet.remove(md);
				break;
			}
		}

		addHighPriority(m);
	}

	public void makeLowPriority(ASManageable m) {
		for (ManagerData md : dataSet) {
			if (md.obj == m) {
				dataSet.remove(md);
				break;
			}
		}

		addLowPriority(m);
	}

	public void setPriority(ASManageable m, int priority) {
		for (ManagerData md : dataSet) {
			if (md.obj == m) {
				dataSet.remove(md);
				break;
			}
		}

		addWithPriority(m, priority);
	}

	public void removeAll() {
		dataSet.clear();
	}

	public static class ManagerData implements Comparable<ManagerData> {
		public ASManageable obj;
		public int priority;

		public ManagerData(ASManageable obj, int priority) {
			this.obj = obj;
			this.priority = priority;
		}

		@Override
		public int compareTo(ManagerData arg0) {
			return arg0.priority - this.priority;
		}
	}

	private Set<ManagerData> dataSet;
	private int highPriority, lowPriority;
	private Set<Integer> usedPriority;

}
