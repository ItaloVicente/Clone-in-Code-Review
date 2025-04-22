package org.eclipse.egit.core;

import org.eclipse.core.runtime.IAdaptable;

public class AdapterUtils {

	private AdapterUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <V> V adapt(Object object, Class<V> target) {
		if (object == null)
			return null;
		if (target.isInstance(object))
			return (V) object;
		if (object instanceof IAdaptable)
			return (V) ((IAdaptable) object).getAdapter(target);
		return null;
	}
}
