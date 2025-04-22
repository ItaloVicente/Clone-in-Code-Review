package org.eclipse.egit.ui.internal.resources;

import org.eclipse.jgit.annotations.NonNull;

public interface IResourceState {

	enum Staged {
		NOT_STAGED,

		MODIFIED,

		ADDED,

		REMOVED,

		RENAMED
	}

	boolean isTracked();

	boolean isIgnored();

	boolean isDirty();

	@NonNull
	Staged staged();

	boolean hasConflicts();

	boolean isAssumeValid();

}
