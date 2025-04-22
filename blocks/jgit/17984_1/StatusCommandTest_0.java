
package org.eclipse.jgit.api;

import java.util.HashSet;
import java.util.Set;

class Sets {
	static <T> Set<T> of(T... elements) {
		Set<T> ret = new HashSet<T>();
		for (T element : elements)
			ret.add(element);
		return ret;
	}
}
