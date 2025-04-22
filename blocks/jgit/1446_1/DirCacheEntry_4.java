	public boolean isSkipWorkTree() {
		return (getExtendedFlags() & SKIP_WORKTREE) == SKIP_WORKTREE;
	}

	public boolean isIntentToAdd() {
		return (getExtendedFlags() & INTENT_TO_ADD) == INTENT_TO_ADD;
	}

