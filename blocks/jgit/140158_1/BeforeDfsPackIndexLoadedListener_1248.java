
package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.events.RepositoryEvent;

public class BeforeDfsPackIndexLoadedEvent
		extends RepositoryEvent<BeforeDfsPackIndexLoadedListener> {
	private final DfsPackFile pack;

	public BeforeDfsPackIndexLoadedEvent(DfsPackFile pack) {
		this.pack = pack;
	}

	public DfsPackFile getPackFile() {
		return pack;
	}

	@Override
	public Class<BeforeDfsPackIndexLoadedListener> getListenerType() {
		return BeforeDfsPackIndexLoadedListener.class;
	}

	@Override
	public void dispatch(BeforeDfsPackIndexLoadedListener listener) {
		listener.onBeforeDfsPackIndexLoaded(this);
	}
}
