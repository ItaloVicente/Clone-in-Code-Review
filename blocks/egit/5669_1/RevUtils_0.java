	private static RevCommit lookupCommit(RevWalk rw, AnyObjectId commit) {
		if (commit == null)
			return null;

		return rw.lookupCommit(commit);
	}

