
package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.events.RepositoryListener;

public interface BeforeDfsPackIndexLoadedListener extends RepositoryListener {
	void onBeforeDfsPackIndexLoaded(BeforeDfsPackIndexLoadedEvent event);
}
