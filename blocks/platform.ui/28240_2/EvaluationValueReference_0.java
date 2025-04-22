
package org.eclipse.ui.internal.navigator.extensions;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class EvaluationReference extends SoftReference {

	private final int hashCode;

	private final Class type;

	public EvaluationReference(Object referent) {
		super(referent);
		hashCode = referent.hashCode();
		type = referent.getClass();
	}
	public EvaluationReference(Object referent, ReferenceQueue queue) {
		super(referent, queue);
		hashCode = referent.hashCode();
		type = referent.getClass();
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
			EvaluationReference otherRef = (EvaluationReference) obj;
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
			return "Evalutation[type="+ type +"]";  //$NON-NLS-1$//$NON-NLS-2$
		return "Evalutation[referent="+ referent +"]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
