
package org.eclipse.jgit.util;

import java.util.Arrays;

public class LongList {
	private long[] entries;

	private int count;

	public LongList() {
		this(10);
	}

	public LongList(int capacity) {
		entries = new long[capacity];
	}

	public int size() {
		return count;
	}

	public long get(int i) {
		if (count <= i)
			throw new ArrayIndexOutOfBoundsException(i);
		return entries[i];
	}

	public boolean contains(long value) {
		for (int i = 0; i < count; i++)
			if (entries[i] == value)
				return true;
		return false;
	}

	public void clear() {
		count = 0;
	}

	public void add(long n) {
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

	public void sort() {
		Arrays.sort(entries
	}

	private void grow() {
		final long[] n = new long[(entries.length + 16) * 3 / 2];
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
