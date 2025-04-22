
package org.eclipse.jgit.util;

public class IntList {
	private int[] entries;

	private int count;

	public IntList() {
		this(10);
	}

	public IntList(int capacity) {
		entries = new int[capacity];
	}

	public int size() {
		return count;
	}

	public boolean contains(int value) {
		for (int i = 0; i < count; i++)
			if (entries[i] == value)
				return true;
		return false;
	}

	public int get(int i) {
		if (count <= i)
			throw new ArrayIndexOutOfBoundsException(i);
		return entries[i];
	}

	public void clear() {
		count = 0;
	}

	public void add(int n) {
		if (count == entries.length)
			grow();
		entries[count++] = n;
	}

	public void set(int index
		if (count < index)
			throw new ArrayIndexOutOfBoundsException(index);
		else if (count == index)
			add(n);
		else
			entries[index] = n;
	}

	public void fillTo(int toIndex
		while (count < toIndex)
			add(val);
	}

	private void grow() {
		final int[] n = new int[(entries.length + 16) * 3 / 2];
		System.arraycopy(entries
		entries = n;
	}

	@Override
	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append('[');
		for (int i = 0; i < count; i++) {
			if (i > 0)
				r.append("
			r.append(entries[i]);
		}
		r.append(']');
		return r.toString();
	}
}
