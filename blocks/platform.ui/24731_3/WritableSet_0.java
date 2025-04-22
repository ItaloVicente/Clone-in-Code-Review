
package org.eclipse.core.databinding.observable.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class FakeSet extends ArrayList implements Set {

	private static final long serialVersionUID = 1L;
	ArrayList items = new ArrayList();

	public FakeSet(Collection items) {
		addAll(items);
	}

}
