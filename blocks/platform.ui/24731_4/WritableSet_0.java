
package org.eclipse.core.databinding.observable.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class FakeSet<E> extends ArrayList<E> implements Set<E> {
	private static final long serialVersionUID = 1L;

	public FakeSet(Collection<E> items) {
		addAll(items);
	}
}
