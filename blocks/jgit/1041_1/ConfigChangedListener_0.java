
package org.eclipse.jgit.events;

public class ConfigChangedEvent extends RepositoryEvent<ConfigChangedListener> {
	@Override
	public Class<ConfigChangedListener> getListenerType() {
		return ConfigChangedListener.class;
	}

	@Override
	public void dispatch(ConfigChangedListener listener) {
		listener.onConfigChanged(this);
	}
}
