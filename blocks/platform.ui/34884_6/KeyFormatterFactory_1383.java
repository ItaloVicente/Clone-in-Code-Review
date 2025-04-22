
package org.eclipse.ui.keys;

import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
import org.eclipse.ui.internal.util.Util;

@Deprecated
public abstract class Key implements Comparable {

	protected final int key;

	Key(final int key) {
		this.key = key;
	}

	@Override
	public final int compareTo(final Object object) {
		return Util.compare(key, ((Key) object).key);
	}

	@Override
	public final boolean equals(final Object object) {
		if (!(object instanceof Key)) {
			return false;
		}

		return key == ((Key) object).key;
	}

	@Override
	public final int hashCode() {
		return Util.hashCode(key);
	}

	@Override
	public final String toString() {
		final IKeyLookup lookup = KeyLookupFactory.getDefault();
		return lookup.formalNameLookup(key);
	}
}
