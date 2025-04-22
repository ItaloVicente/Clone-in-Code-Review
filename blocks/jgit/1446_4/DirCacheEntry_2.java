	public boolean isSkipWorkTree() {
		return (getExtendedFlags() & SKIP_WORKTREE) != 0;
	}

	public boolean isIntentToAdd() {
		return (getExtendedFlags() & INTENT_TO_ADD) != 0;
	}

