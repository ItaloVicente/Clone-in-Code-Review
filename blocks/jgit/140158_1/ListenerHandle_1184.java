
package org.eclipse.jgit.events;

public interface IndexChangedListener extends RepositoryListener {
	void onIndexChanged(IndexChangedEvent event);
}
