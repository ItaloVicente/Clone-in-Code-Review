
package org.eclipse.jgit.events;

public class IndexChangedEvent extends RepositoryEvent<IndexChangedListener> {
	private boolean internal;

	public IndexChangedEvent(boolean internal) {
		this.internal = internal;
	}

	public boolean isInternal() {
		return internal;
	}

	@Override
	public Class<IndexChangedListener> getListenerType() {
		return IndexChangedListener.class;
	}

	@Override
	public void dispatch(IndexChangedListener listener) {
		listener.onIndexChanged(this);
	}
}
