
package org.eclipse.jgit.lib;

import java.util.HashSet;
import java.util.Set;

public class Sets {
	@SafeVarargs
	public static <T> Set<T> of(T... elements) {
		Set<T> ret = new HashSet<>();
		for (T element : elements)
			ret.add(element);
		return ret;
	}
}
