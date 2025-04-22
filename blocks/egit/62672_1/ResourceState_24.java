package org.eclipse.egit.ui.internal.resources;

import org.eclipse.jgit.annotations.NonNull;

public interface IResourceState {

	enum StagingState {
		NOT_STAGED,

		MODIFIED,

		ADDED,

		REMOVED,

		RENAMED
	}

	boolean isTracked();

	boolean isIgnored();

	boolean isDirty();

	boolean isMissing();

	@NonNull
	StagingState getStagingState();

	boolean isStaged();

	boolean hasConflicts();

	boolean isAssumeValid();

}
