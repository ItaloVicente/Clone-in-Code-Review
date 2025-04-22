	private boolean isContainedInAnyRemoteBranch(RevCommit commit) {
		try {
			Collection<Ref> refs = repository.getRefDatabase().getRefs(
					Constants.R_REMOTES).values();
			return RevUtils.isContainedInAnyRef(repository, commit, refs);
		} catch (IOException e) {
			return false;
		}
	}

