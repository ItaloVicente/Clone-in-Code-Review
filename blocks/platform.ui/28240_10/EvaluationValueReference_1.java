
package org.eclipse.ui.internal.navigator.extensions;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class EvaluationValueReference extends SoftReference {
	private Reference /* <EvaluationReference> */ refToKey;

	public EvaluationValueReference(Object referrent, EvaluationReference key) {
		super(referrent);
		this.refToKey = new WeakReference(key);
	}

	public EvaluationValueReference(Object referrent, EvaluationReference key,
			ReferenceQueue queue) {
		super(referrent, queue);
		this.refToKey = new WeakReference(key);
	}

	public EvaluationReference getKey() {
		return (EvaluationReference) refToKey.get();
	}

	void swapKey(EvaluationValueReference otherValue) {
		Reference tmp = refToKey;
		this.refToKey = otherValue.refToKey;
		otherValue.refToKey = tmp;
	}

	public void clear() {
		super.clear();
		refToKey.clear();
	}

	private static String toStringArrayAware(Object o) {
		if (o instanceof Object[]) {
			return java.util.Arrays.asList((Object[]) o).toString();
		}
		return String.valueOf(o);
	}

	public java.lang.String toString() {
		Object myRef = get();
		return "EvaluationValueReference[" + (myRef == null ? "(collected)" : toStringArrayAware(myRef)) + ']'; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
