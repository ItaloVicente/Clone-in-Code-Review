	private static ObjectId getTree(RevCommit commit) {
		if (commit != null)
			return commit.getTree();
		else {
			return ObjectId.zeroId();
		}
	}

