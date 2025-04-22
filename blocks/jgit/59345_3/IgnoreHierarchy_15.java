
package org.eclipse.jgit.events;

public interface DotFileChangedListener extends RepositoryListener {
	void onDotFileChanged(DotFileChangedEvent event);
}
