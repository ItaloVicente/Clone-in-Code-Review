package org.eclipse.egit.ui.internal.history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;

public class FindResults {
	private Map<Integer, Integer> matchesMap = new LinkedHashMap<>();

	private List<RevObject> revObjList = new ArrayList<>();

	Integer[] keysArray;

	private int matchesCount;

	private RevFlag highlight;

	private boolean overflow;

	private final CopyOnWriteArrayList<IFindListener> listeners = new CopyOnWriteArrayList<>();

	public void addFindListener(IFindListener listener) {
		listeners.addIfAbsent(listener);
	}

	public void removeFindListener(IFindListener listener) {
		listeners.remove(listener);
	}

	public synchronized boolean isFoundAt(int index) {
		return matchesMap.containsKey(Integer.valueOf(index));
	}

	public synchronized int getIndexAfter(int index) {
		Integer[] matches = getkeysArray();
		int sres = Arrays.binarySearch(matches, Integer.valueOf(index));
		if (sres >= 0 && sres != matches.length - 1) {
			return matches[sres + 1].intValue();
		} else if (sres < 0) {
			sres = -sres - 1;
			if (sres < matches.length) {
				return matches[sres].intValue();
			}
		}

		return -1;
	}

	public synchronized int getIndexBefore(int index) {
		Integer[] matches = getkeysArray();
		int sres = Arrays.binarySearch(matches, Integer.valueOf(index));
		if (sres >= 0 && sres != 0) {
			return matches[sres - 1].intValue();
		} else if (sres < -1) {
			sres = -sres;
			return matches[sres - 2].intValue();
		}

		return -1;
	}

	public synchronized int getFirstIndex() {
		Iterator iter = matchesMap.keySet().iterator();
		if (iter.hasNext()) {
			return ((Integer) iter.next()).intValue();
		}

		return -1;
	}

	public synchronized int getLastIndex() {
		Integer[] matches = getkeysArray();
		if (matches.length > 0) {
			return matches[matches.length - 1].intValue();
		}

		return -1;
	}

	public synchronized int getMatchNumberFor(int index) {
		Integer ix = matchesMap.get(Integer.valueOf(index));
		if (ix != null) {
			return ix.intValue();
		}

		return -1;
	}

	public int size() {
		return matchesCount;
	}

	public synchronized void clear() {
		if (highlight != null) {
			for (RevObject o : revObjList) {
				o.remove(highlight);
			}
		}
		matchesMap.clear();
		revObjList.clear();
		keysArray = null;
		boolean hadItems = matchesCount > 0;
		matchesCount = 0;
		if (hadItems) {
			for (IFindListener listener : listeners) {
				listener.cleared();
			}
		}
	}

	public synchronized void add(int matchIx, RevObject revObj) {
		matchesMap.put(Integer.valueOf(matchIx), Integer
				.valueOf(++matchesCount));
		revObjList.add(revObj);
		revObj.add(highlight);
		keysArray = null;
		for (IFindListener listener : listeners) {
			listener.itemAdded(matchIx, revObj);
		}
	}

	private Integer[] getkeysArray() {
		if (keysArray == null) {
			keysArray = matchesMap.keySet().toArray(
					new Integer[matchesMap.size()]);
		}

		return keysArray;
	}

	synchronized void setHighlightFlag(RevFlag hFlag) {
		if (highlight != null) {
			clear();
		}
		this.highlight = hFlag;
	}

	synchronized void setOverflow() {
		overflow = true;
	}

	synchronized boolean isOverflow() {
		return overflow;
	}
}
