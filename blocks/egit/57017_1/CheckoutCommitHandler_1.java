		List<Ref> nodes;
		try {
			nodes = getBranchesOfCommit(getSelection(event), repo, true);
		} catch (IOException e) {
			throw new ExecutionException(
					UIText.AbstractHistoryCommitHandler_cantGetBranches,
					e);
		}
