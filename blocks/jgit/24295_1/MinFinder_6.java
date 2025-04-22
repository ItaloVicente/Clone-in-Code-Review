
package org.eclipse.jgit.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class LinearMinFinder<T> extends MinFinder<T> {

	public LinearMinFinder(List<List<T>> lists
			int size) {
		super(lists
	}

	public T peek() {
		List<T> minList = findMinList();

		if (minList == null)
			return null;

		return minList.get(minList.size() - 1);
	}

	public T pop() {
		List<T> minList = findMinList();

		if (minList == null)
			return null;

		T retval = minList.remove(minList.size() - 1);
		if (minList.size() <= 0)
			lists.remove(minList);

		return retval;
	}

	protected List<T> findMinList() {
		if (lists.isEmpty()) {
			return null;
		}

		Iterator<List<T>> it = lists.iterator();
		List<T> minList = it.next();
		T minValue = minList.get(minList.size() - 1);
		while (it.hasNext()) {
			List<T> list = it.next();
			if (comparator.compare(minValue
				minList = list;
				minValue = list.get(minList.size() - 1);
			}
		}

		return minList;
	}
}
