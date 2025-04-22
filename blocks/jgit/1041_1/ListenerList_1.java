
package org.eclipse.jgit.events;

public interface ConfigChangedListener extends RepositoryListener {
	void onConfigChanged(ConfigChangedEvent event);
}
