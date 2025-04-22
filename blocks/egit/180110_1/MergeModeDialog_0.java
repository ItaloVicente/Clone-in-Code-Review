package org.eclipse.egit.ui.internal.merge;

import org.eclipse.jgit.annotations.NonNull;

public enum MergeInputMode {

	WORKTREE,

	STAGE_2;

	@NonNull
	public static MergeInputMode fromInteger(int i) {
		if (i == 1) {
			return WORKTREE;
		}
		return STAGE_2;
	}

	public int toInteger() {
		return ordinal() + 1;
	}
}
