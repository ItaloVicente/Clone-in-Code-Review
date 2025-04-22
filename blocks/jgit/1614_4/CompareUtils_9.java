
package org.eclipse.jgit.util;

public class CompareUtils {

	public static <E> boolean areEqual(E obj1
		return (obj1 == obj2) || (obj1 != null && obj1.equals(obj2));
	}

	private CompareUtils() {
	}
}
