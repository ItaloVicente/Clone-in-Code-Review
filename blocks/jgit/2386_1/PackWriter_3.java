
package org.eclipse.jgit.storage.pack;

class IntSet {
	private int[] set;

	private int cnt;

	IntSet() {
		set = new int[64];
	}

	boolean add(int key) {
		int high = cnt;
		int low = 0;

		if (high == 0) {
			set[0] = key;
			cnt = 1;
			return true;
		}

		do {
			int p = (low + high) >>> 1;
			if (key < set[p])
				high = p;
			else if (key == set[p])
				return false;
			else
				low = p + 1;
		} while (low < high);

		if (cnt == set.length) {
			int[] n = new int[set.length * 2];
			System.arraycopy(set
			set = n;
		}

		if (low < cnt)
			System.arraycopy(set
		set[low] = key;
		cnt++;
		return true;
	}
}
