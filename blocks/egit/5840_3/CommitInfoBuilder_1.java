		try {
			return findBranchesReachableFrom(commit, revWalk, allRefs);
		} finally {
			revWalk.dispose();
		}
	}

	private static List<Ref> findBranchesReachableFrom(RevCommit commit,
			RevWalk revWalk, Collection<Ref> refs)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {

