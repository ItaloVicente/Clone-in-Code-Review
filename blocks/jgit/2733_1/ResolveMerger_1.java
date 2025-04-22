	private boolean isIndexDirty() {
		final int modeI = tw.getRawMode(T_INDEX);
		final int modeO = tw.getRawMode(T_OURS);

		final boolean isDirty = nonTree(modeI)
				&& !(tw.idEqual(T_INDEX
		if (isDirty)
			failingPaths
					.put(tw.getPathString()
		return isDirty;
	}

	private boolean isWorktreeDirty() {
		if (inCore)
			return false;

		final int modeF = tw.getRawMode(T_FILE);
		final int modeO = tw.getRawMode(T_OURS);

		final boolean isDirty = nonTree(modeF)
				&& !(tw.idEqual(T_FILE
		if (isDirty)
			failingPaths.put(tw.getPathString()
					MergeFailureReason.DIRTY_WORKTREE);
		return isDirty;
	}

