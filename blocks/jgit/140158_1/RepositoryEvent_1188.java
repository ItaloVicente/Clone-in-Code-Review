
package org.eclipse.jgit.events;

public interface RefsChangedListener extends RepositoryListener {
	void onRefsChanged(RefsChangedEvent event);
}
