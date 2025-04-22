
package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.events.RepositoryListener;

public interface DfsPacksChangedListener extends RepositoryListener {
	void onPacksChanged(DfsPacksChangedEvent event);
}
