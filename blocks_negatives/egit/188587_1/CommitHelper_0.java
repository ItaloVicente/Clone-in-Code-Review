		committer = config.getCommitterName();
		final String committerEmail = config.getCommitterEmail();

		if (isMergedResolved || isCherryPickResolved) {
			commitMessage = getMergeResolveMessage(mergeRepository);
		}
