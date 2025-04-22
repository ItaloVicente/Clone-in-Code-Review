	private List<Ref> getBranchesExcludingCurrent(GitHistoryPage page,
			Repository repo) {
		try {
			return getBranchesOfCommit(page, repo, true);
		} catch (IOException e) {
			return Collections.<Ref> emptyList();
		}
	}

