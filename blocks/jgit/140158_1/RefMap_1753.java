
package org.eclipse.jgit.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;

public class RefList<T extends Ref> implements Iterable<Ref> {
	private static final RefList<Ref> EMPTY = new RefList<>(new Ref[0]

	@SuppressWarnings("unchecked")
	public static <T extends Ref> RefList<T> emptyList() {
		return (RefList<T>) EMPTY;
	}

	final Ref[] list;

	final int cnt;

	RefList(Ref[] list
		this.list = list;
		this.cnt = cnt;
	}

	protected RefList(RefList<T> src) {
		this.list = src.list;
		this.cnt = src.cnt;
	}

	@Override
	public Iterator<Ref> iterator() {
		return new Iterator<Ref>() {
			private int idx;

			@Override
			public boolean hasNext() {
				return idx < cnt;
			}

			@Override
			public Ref next() {
				if (idx < cnt)
					return list[idx++];
				throw new NoSuchElementException();
			}

			@Override
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
		Builder<T> r = new Builder<>(Math.max(16
		r.addAll(list
		return r;
	}

	public final RefList<T> set(int idx
		Ref[] newList = new Ref[cnt];
		System.arraycopy(list
		newList[idx] = ref;
		return new RefList<>(newList
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
		return new RefList<>(newList
	}

	public final RefList<T> remove(int idx) {
		if (cnt == 1)
			return emptyList();
		Ref[] newList = new Ref[cnt - 1];
		if (0 < idx)
			System.arraycopy(list
		if (idx + 1 < cnt)
			System.arraycopy(list
		return new RefList<>(newList
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

	public static <T extends Ref> Collector<T
			@Nullable BinaryOperator<T> mergeFunction) {
		return Collector.of(
				() -> new Builder<>()
				Builder<T>::add
					Builder<T> b = new Builder<>();
					b.addAll(b1);
					b.addAll(b2);
					return b;
				}
					if (mergeFunction != null) {
						b.sort();
						b.dedupe(mergeFunction);
					}
					return b.toRefList();
				});
	}

	public static class Builder<T extends Ref> {
		private Ref[] list;

		private int size;

		public Builder() {
			this(16);
		}

		public Builder(int capacity) {
			list = new Ref[Math.max(capacity
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

		public void addAll(Builder other) {
			addAll(other.list
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

		@SuppressWarnings("unchecked")
		void dedupe(BinaryOperator<T> mergeFunction) {
			if (size == 0) {
				return;
			}
			int lastElement = 0;
			for (int i = 1; i < size; i++) {
				if (RefComparator.INSTANCE.compare(list[lastElement]
						list[i]) == 0) {
					list[lastElement] = mergeFunction
							.apply((T) list[lastElement]
				} else {
					list[lastElement + 1] = list[i];
					lastElement++;
				}
			}
			size = lastElement + 1;
			Arrays.fill(list
		}

		public RefList<T> toRefList() {
			return new RefList<>(list
		}

		@Override
		public String toString() {
			return toRefList().toString();
		}
	}
}
