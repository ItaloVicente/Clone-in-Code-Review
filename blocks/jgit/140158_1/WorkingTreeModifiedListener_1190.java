package org.eclipse.jgit.events;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.annotations.NonNull;

public class WorkingTreeModifiedEvent
		extends RepositoryEvent<WorkingTreeModifiedListener> {

	private Collection<String> modified;

	private Collection<String> deleted;

	public WorkingTreeModifiedEvent(Collection<String> modified
			Collection<String> deleted) {
		this.modified = modified;
		this.deleted = deleted;
	}

	public boolean isEmpty() {
		return (modified == null || modified.isEmpty())
				&& (deleted == null || deleted.isEmpty());
	}

	@NonNull
	public Collection<String> getModified() {
		Collection<String> result = modified;
		if (result == null) {
			result = Collections.emptyList();
			modified = result;
		}
		return result;
	}

	@NonNull
	public Collection<String> getDeleted() {
		Collection<String> result = deleted;
		if (result == null) {
			result = Collections.emptyList();
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
