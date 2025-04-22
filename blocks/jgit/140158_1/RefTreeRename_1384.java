
package org.eclipse.jgit.internal.storage.reftree;

import org.eclipse.jgit.lib.RefDatabase;

public class RefTreeNames {

	public static boolean isRefTree(RefDatabase refdb
		if (refdb instanceof RefTreeDatabase) {
			RefTreeDatabase b = (RefTreeDatabase) refdb;
			if (ref.equals(b.getTxnCommitted())) {
				return true;
			}

			String namespace = b.getTxnNamespace();
			if (namespace != null
					&& ref.startsWith(namespace)
					&& !ref.startsWith(namespace + STAGE)) {
				return true;
			}
		}
		return false;
	}

	private RefTreeNames() {
	}
}
