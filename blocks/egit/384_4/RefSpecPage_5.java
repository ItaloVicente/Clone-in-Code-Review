		final String actRemoteName;
		if (remoteName == null)
			actRemoteName = validatedRepoSelection.getConfigName();
		else
			actRemoteName = remoteName;
		if (initialSpecSize < 0)
			initialSpecSize = listRemotesOp.getRemoteRefs().size();
