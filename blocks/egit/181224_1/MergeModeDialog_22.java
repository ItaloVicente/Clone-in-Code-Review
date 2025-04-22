package org.eclipse.egit.ui.internal.merge;

import org.eclipse.jgit.annotations.NonNull;

public enum MergeInputMode {

	WORKTREE,

	STAGE_2,

	MERGED_OURS;

	@NonNull
	public static MergeInputMode fromInteger(int i) {
		if (i == 1) {
			return WORKTREE;
		}
		if (i == 2) {
			return STAGE_2;
		}
		return MERGED_OURS;
	}

	public int toInteger() {
		return ordinal() + 1;
	}
}
