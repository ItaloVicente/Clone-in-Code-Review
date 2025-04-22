	public void setSkipWorkTree(final boolean skip) {
		if (!skip && !isExtended()) {
			return;
		}

		if (!isExtended()) {
			extend();
			setExtended(true);
		}

		if (skip) {
			int extendedFlags = getExtendedFlags();
			extendedFlags |= SKIP_WORKTREE;
			setExtendedFlags(extendedFlags);
		} else if (isExtended() && skip) {
			int extendedFlags = getExtendedFlags();
			extendedFlags |= SKIP_WORKTREE;
			setExtendedFlags(extendedFlags);
		}
	}

