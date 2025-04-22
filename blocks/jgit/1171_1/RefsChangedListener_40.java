
package org.eclipse.jgit.events;

public class RefsChangedEvent extends RepositoryEvent<RefsChangedListener> {
	@Override
	public Class<RefsChangedListener> getListenerType() {
		return RefsChangedListener.class;
	}

	@Override
	public void dispatch(RefsChangedListener listener) {
		listener.onRefsChanged(this);
	}
}
