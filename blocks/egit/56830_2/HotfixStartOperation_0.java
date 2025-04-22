		GitFlowConfig config = repository.getConfig();
		String branchName = config.getHotfixBranchName(versionName);
		RevCommit head = repository.findHead(config.getMaster());
		if (head == null) {
			throw new IllegalStateException(NLS.bind(CoreText.StartOperation_unableToFindCommitFor, config.getDevelop()));
		}
		start(monitor, branchName, head);
