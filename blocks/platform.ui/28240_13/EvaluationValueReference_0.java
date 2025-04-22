
package org.eclipse.ui.internal.navigator.extensions;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class EvaluationReference<T> extends SoftReference<T> {

	private final int hashCode;

	private final String typeName;

	public EvaluationReference(T referent) {
		super(referent);
		hashCode = referent.hashCode();
		typeName = referent.getClass().getName();
	}

	public EvaluationReference(T referent, ReferenceQueue<? super T> queue) {
		super(referent, queue);
		hashCode = referent.hashCode();
		typeName = referent.getClass().getName();
	}

	public int hashCode() {
		return hashCode;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj == this)
			return true;
		else if (obj instanceof EvaluationReference) {
			Object myObj = get();
			if (myObj == null)
				return false;
			EvaluationReference<?> otherRef = (EvaluationReference<?>) obj;
			if (hashCode != otherRef.hashCode)
				return false;
			Object otherObj = otherRef.get();
			if (otherObj == null)
				return false;
			return myObj == otherObj || myObj.equals(otherObj);
		}
		return false;
	}

	public String toString() {
		Object referent = get();
		if(referent == null)
			return "Evalutation[type="+ typeName +"]";  //$NON-NLS-1$//$NON-NLS-2$
		return "Evalutation[referent="+ referent +"]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
