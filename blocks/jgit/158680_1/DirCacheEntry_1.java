	public void setSkipWorktree(boolean skipWorktree) {
		if (!isExtended()) {
			info[infoOffset + P_FLAGS] |= (byte) EXTENDED;
		}
		if (skipWorktree)
			info[infoOffset + P_FLAGS2] |= (byte) SKIP_WORKTREE;
		else
			info[infoOffset + P_FLAGS2] &= (byte) ~SKIP_WORKTREE;
	}

