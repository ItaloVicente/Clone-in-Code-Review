
package org.eclipse.jgit.revwalk;

public final class DenseIntAnnotation extends RevAnnotation<Integer> {
	private static final int[] EMPTY_ARRAY = {};

	private int[] values = EMPTY_ARRAY;

	public Integer get(RevCommit obj) {
		return Integer.valueOf(getInt(obj));
	}

	public void set(RevCommit obj
		setInt(obj
	}

	public int getInt(RevCommit obj) {
		final int id = obj.annotationId;
		return id < values.length ? values[id] : 0;
	}

	public void set(RevCommit obj
		setInt(obj
	}

	public void setInt(RevCommit obj
		final int id = obj.annotationId;
		ensureCapacity(id);
		values[id] = value;
	}

	public int addAndGet(RevCommit obj
		final int id = obj.annotationId;
		ensureCapacity(id);
		return values[id] += delta;
	}

	public int getAndAdd(RevCommit obj
		final int id = obj.annotationId;
		ensureCapacity(id);
		final int old = values[id];
		values[id] = old + delta;
		return old;
	}

	public int getAndDecrement(RevCommit obj) {
		return getAndAdd(obj
	}

	public int getAndIncrement(RevCommit obj) {
		return getAndAdd(obj
	}

	public int decrementAndGet(RevCommit obj) {
		return addAndGet(obj
	}

	public int incrementAndGet(RevCommit obj) {
		return addAndGet(obj
	}

	private void ensureCapacity(final int annotationId) {
		if (annotationId <= values.length) {
			int[] n = new int[newSize(annotationId)];
			System.arraycopy(values
			values = n;
		}
	}

	private int newSize(int annotationId) {
		return Math.max(16
	}
}
