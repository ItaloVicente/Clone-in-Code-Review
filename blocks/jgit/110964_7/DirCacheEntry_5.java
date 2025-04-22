	public void setSkipWorkTree(final boolean skip) {
		if (!isExtended()) {
			extend();
			setExtended(true);
		}

		if (skip) {
			int extendedFlags = getExtendedFlags();
			extendedFlags |= SKIP_WORKTREE;
			setExtendedFlags(extendedFlags);
		} else {
			int extendedFlags = getExtendedFlags();
			extendedFlags |= ~SKIP_WORKTREE;
			setExtendedFlags(extendedFlags);
		}
	}

