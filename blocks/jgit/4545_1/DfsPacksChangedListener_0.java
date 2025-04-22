
package org.eclipse.jgit.storage.dfs;

import org.eclipse.jgit.events.RepositoryEvent;

public class DfsPacksChangedEvent
		extends RepositoryEvent<DfsPacksChangedListener> {
	@Override
	public Class<DfsPacksChangedListener> getListenerType() {
		return DfsPacksChangedListener.class;
	}

	@Override
	public void dispatch(DfsPacksChangedListener listener) {
		listener.onPacksChanged(this);
	}
}
