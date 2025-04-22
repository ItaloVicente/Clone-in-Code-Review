
package org.eclipse.jgit.transport;

import java.util.Map;

import org.eclipse.jgit.lib.Ref;

public interface RefFilter {
	public static final RefFilter DEFAULT = new RefFilter() {
		public Map<String
			return refs;
		}
	};

	public Map<String
}
