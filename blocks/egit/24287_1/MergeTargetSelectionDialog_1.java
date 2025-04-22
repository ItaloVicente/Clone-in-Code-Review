		MergeConfig config = MergeConfig.getConfigForCurrentBranch(repo);
		fastForwardMode = config.getFastForwardMode();
		mergeSquash = config.isSquash();
		if (mergeSquash)
			mergeCommit = false;
		else
			mergeCommit = config.isCommit();
