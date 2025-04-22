
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Ref;

public interface RefFilter {
	public static final RefFilter DEFAULT = new RefFilter() {
		public boolean show(final Ref ref) {
			return true;
		}
	};

	public boolean show(final Ref ref);
}
