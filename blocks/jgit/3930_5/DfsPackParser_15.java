
package org.eclipse.jgit.storage.dfs;

final class DfsPackKey {
	final int hash;

	DfsPackKey() {
		hash = System.identityHashCode(this) * 31;
	}
}
