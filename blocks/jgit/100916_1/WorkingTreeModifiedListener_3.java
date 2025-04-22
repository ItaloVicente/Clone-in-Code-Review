package org.eclipse.jgit.events;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;

public class WorkingTreeModifiedEvent
		extends RepositoryEvent<WorkingTreeModifiedListener> {

	private Set<String> modified;

	private Set<String> deleted;

	public WorkingTreeModifiedEvent(Set<String> modified
		this.modified = modified;
		this.deleted = deleted;
	}

	public boolean isEmpty() {
		return (modified == null || modified.isEmpty())
				&& (deleted == null || deleted.isEmpty());
	}

	public @NonNull Set<String> getModified() {
		Set<String> result = modified;
		if (result == null) {
			result = new HashSet<>();
			modified = result;
		}
		return result;
	}

	public @NonNull Set<String> getDeleted() {
		Set<String> result = deleted;
		if (result == null) {
			result = new HashSet<>();
			deleted = result;
		}
		return result;
	}

	@Override
	public Class<WorkingTreeModifiedListener> getListenerType() {
		return WorkingTreeModifiedListener.class;
	}

	@Override
	public void dispatch(WorkingTreeModifiedListener listener) {
		listener.onWorkingTreeModified(this);
	}
}
