	private void lookupRefs() throws TeamException {
		RevWalk walk = new RevWalk(repository);
		try {
			if (refName != null) {
				newCommit = walk.parseCommit(repository.resolve(refName));
			}
			if (commitId != null) {
				newCommit = walk.parseCommit(commitId);
			}
		} catch (IOException e) {
			throw new TeamException(NLS.bind(
					CoreText.BranchOperation_mappingCommit, refName), e);
		}

		try {
			oldCommit = walk.parseCommit(repository.resolve(Constants.HEAD));
		} catch (IOException e) {
			throw new TeamException(CoreText.BranchOperation_mappingCommitHead,
					e);
