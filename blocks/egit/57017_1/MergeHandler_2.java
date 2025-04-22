		List<Ref> nodes;
		try {
			nodes = getBranchesOfCommit(getSelection(event), repository, true);
		} catch (IOException e) {
			throw new ExecutionException(
					UIText.AbstractHistoryCommitHandler_cantGetBranches,
					e);
		}

