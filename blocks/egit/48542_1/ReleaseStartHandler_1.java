		Shell activeShell = HandlerUtil.getActiveShell(event);

		doExecute(gfRepo, startCommitSha1, activeShell);

		return null;
	}

	void doExecute(GitFlowRepository gfRepo,
			final String startCommitSha1, Shell activeShell) {
