		return findBranchesReachableFrom(commit
				NullProgressMonitor.INSTANCE);
	}

	public static List<Ref> findBranchesReachableFrom(RevCommit commit
			RevWalk revWalk
			throws MissingObjectException
			IOException {
