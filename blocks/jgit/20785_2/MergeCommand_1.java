	private void fallBackToConfiguration() {
		MergeConfig config = MergeConfig.getConfigForCurrentBranch(repo);
		if (squash == null)
			squash = Boolean.valueOf(config.isSquash());
		if (commit == null)
			commit = Boolean.valueOf(config.isCommit());
		if (fastForwardMode == null)
			fastForwardMode = config.getFastfForwardMode();
	}

