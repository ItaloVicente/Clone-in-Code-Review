
package org.eclipse.jgit.revwalk;

public final class DenseAnnotation<V extends Object> extends RevAnnotation<V> {
	private V[] values = emptyArray();

	public V get(RevCommit obj) {
		final int annotationId = obj.annotationId;
		if (annotationId < values.length)
			return values[annotationId];
		return null;
	}

	public void set(RevCommit obj
		final int annotationId = obj.annotationId;
		if (annotationId <= values.length) {
			V[] n = newArray(newSize(annotationId));
			System.arraycopy(values
			values = n;
		}
		values[annotationId] = value;
	}

	private int newSize(int annotationId) {
		return Math.max(16
	}

	@SuppressWarnings("unchecked")
	private static <V> V[] newArray(int size) {
		return (V[]) new Object[size];
	}

	private static final Object[] EMPTY_ARRAY = {};

	@SuppressWarnings("unchecked")
	private static <V> V[] emptyArray() {
		return (V[]) EMPTY_ARRAY;
	}
}
