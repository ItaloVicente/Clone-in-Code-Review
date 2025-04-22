
package org.eclipse.jgit.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;

public class RefList<T extends Ref> implements Iterable<Ref> {
	private static final RefList<Ref> EMPTY = new RefList<Ref>(new Ref[0]

	@SuppressWarnings("unchecked")
	public static <T extends Ref> RefList<T> emptyList() {
		return (RefList<T>) EMPTY;
	}

	private final Ref[] list;

	private final int cnt;

	RefList(Ref[] list
		this.list = list;
		this.cnt = cnt;
	}

	protected RefList(RefList<T> src) {
		this.list = src.list;
		this.cnt = src.cnt;
	}

	public Iterator<Ref> iterator() {
		return new Iterator<Ref>() {
			private int idx;

			public boolean hasNext() {
				return idx < cnt;
			}

			public Ref next() {
				if (idx < cnt)
					return list[idx++];
				throw new NoSuchElementException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public final List<Ref> asList() {
		final List<Ref> r = Arrays.asList(list).subList(0
		return Collections.unmodifiableList(r);
	}

	public final int size() {
		return cnt;
	}

	public final boolean isEmpty() {
		return cnt == 0;
	}

	public final int find(String name) {
		int high = cnt;
		if (high == 0)
			return -1;
		int low = 0;
		do {
			final int mid = (low + high) >>> 1;
			final int cmp = RefComparator.compareTo(list[mid]
			if (cmp < 0)
				low = mid + 1;
			else if (cmp == 0)
				return mid;
			else
				high = mid;
		} while (low < high);
		return -(low + 1);
	}

	public final boolean contains(String name) {
		return 0 <= find(name);
	}

	public final T get(String name) {
		int idx = find(name);
		return 0 <= idx ? get(idx) : null;
	}

	@SuppressWarnings("unchecked")
	public final T get(int idx) {
		return (T) list[idx];
	}

	public final Builder<T> copy(int n) {
		Builder<T> r = new Builder<T>(Math.max(16
		r.addAll(list
		return r;
	}

	public final RefList<T> set(int idx
		Ref[] newList = new Ref[cnt];
		System.arraycopy(list
		newList[idx] = ref;
		return new RefList<T>(newList
	}

	public final RefList<T> add(int idx
		if (idx < 0)
			idx = -(idx + 1);

		Ref[] newList = new Ref[cnt + 1];
		if (0 < idx)
			System.arraycopy(list
		newList[idx] = ref;
		if (idx < cnt)
			System.arraycopy(list
		return new RefList<T>(newList
	}

	public final RefList<T> remove(int idx) {
		if (cnt == 1)
			return emptyList();
		Ref[] newList = new Ref[cnt - 1];
		if (0 < idx)
			System.arraycopy(list
		if (idx + 1 < cnt)
			System.arraycopy(list
		return new RefList<T>(newList
	}

	public final RefList<T> put(T ref) {
		int idx = find(ref.getName());
		if (0 <= idx)
			return set(idx
		return add(idx
	}

	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append('[');
		if (cnt > 0) {
			r.append(list[0]);
			for (int i = 1; i < cnt; i++) {
				r.append("
				r.append(list[i]);
			}
		}
		r.append(']');
		return r.toString();
	}

	public static class Builder<T extends Ref> {
		private Ref[] list;

		private int size;

		public Builder() {
			this(16);
		}

		public Builder(int capacity) {
			list = new Ref[capacity];
		}

		public int size() {
			return size;
		}

		@SuppressWarnings("unchecked")
		public T get(int idx) {
			return (T) list[idx];
		}

		public void remove(int idx) {
			System.arraycopy(list
			size--;
		}

		public void add(T ref) {
			if (list.length == size) {
				Ref[] n = new Ref[size * 2];
				System.arraycopy(list
				list = n;
			}
			list[size++] = ref;
		}

		public void addAll(Ref[] src
			if (list.length < size + cnt) {
				Ref[] n = new Ref[Math.max(size * 2
				System.arraycopy(list
				list = n;
			}
			System.arraycopy(src
			size += cnt;
		}

		public void set(int idx
			list[idx] = ref;
		}

		public void sort() {
			Arrays.sort(list
		}

		public RefList<T> toRefList() {
			return new RefList<T>(list
		}

		@Override
		public String toString() {
			return toRefList().toString();
		}
	}
}
