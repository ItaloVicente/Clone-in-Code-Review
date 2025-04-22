
package org.eclipse.jgit.events;

public interface WorkingTreeModifiedListener extends RepositoryListener {

	void onWorkingTreeModified(WorkingTreeModifiedEvent event);
}
