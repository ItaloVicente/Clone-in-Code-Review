
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.RefDatabase.ALL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.lib.Ref;
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

	public static Collection<Ref> allRefs(RefDatabase refdb)
			throws IOException {
		Collection<Ref> refs = refdb.getRefs(ALL).values();
		if (!(refdb instanceof RefTreeDatabase)) {
			return refs;
		}

		RefDatabase bootstrap = ((RefTreeDatabase) refdb).getBootstrap();
		Collection<Ref> br = bootstrap.getRefs(ALL).values();
		List<Ref> all = new ArrayList<>(refs.size() + br.size());
		all.addAll(refs);
		all.addAll(br);
		return all;
	}

	private RefTreeNames() {
	}
}
