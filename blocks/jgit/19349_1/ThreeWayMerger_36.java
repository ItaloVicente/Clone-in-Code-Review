		RevCommit baseCommit = (baseCommitId != null) ? walk
				.parseCommit(baseCommitId) : getBaseCommit(sourceCommits[0]
				sourceCommits[1]);
		if (baseCommit == null) {
			baseCommitId = null;
			return new EmptyTreeIterator();
		} else {
			baseCommitId = baseCommit.toObjectId();
			return openTree(baseCommit.getTree());
		}
