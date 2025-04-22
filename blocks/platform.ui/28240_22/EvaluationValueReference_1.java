package org.eclipse.ui.internal.navigator.extensions;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class EvaluationReference<T> extends SoftReference<T> {
	private final int hashCode;

	public EvaluationReference(T referent) {
		super(referent);
		hashCode = referent.hashCode();
	}

	public EvaluationReference(T referent, ReferenceQueue<? super T> queue) {
		super(referent, queue);
		hashCode = referent.hashCode();
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj == this) {
			return true;
		} else if (obj instanceof EvaluationReference) {
			Object myObj = get();
			if (myObj == null) return false;
			EvaluationReference<?> otherRef = (EvaluationReference<?>) obj;
			if (hashCode != otherRef.hashCode) return false;
			Object otherObj = otherRef.get();
			if (otherObj == null) return false;
			return myObj == otherObj || myObj.equals(otherObj);
		}
		return false;
	}

	@Override
	public String toString() {
		Object referent = get();
		return "Evalutation[" + (referent == null ? "(collected)" : "referent=" + referent) + ']'; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
