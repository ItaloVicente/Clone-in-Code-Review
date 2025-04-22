		RevCommit baseCommit = getBaseCommit(sourceCommits[0]
		if (baseCommit == null) {
			baseCommitId = null;
			return new EmptyTreeIterator();
		} else {
			baseCommitId = baseCommit.toObjectId();
			return openTree(baseCommit.getTree());
		}
