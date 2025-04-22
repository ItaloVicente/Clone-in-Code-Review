
package org.eclipse.jgit.events;

public class IndexChangedEvent extends RepositoryEvent<IndexChangedListener> {
	@Override
	public Class<IndexChangedListener> getListenerType() {
		return IndexChangedListener.class;
	}

	@Override
	public void dispatch(IndexChangedListener listener) {
		listener.onIndexChanged(this);
	}
}
