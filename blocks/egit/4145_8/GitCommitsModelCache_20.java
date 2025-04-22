	private static void addTreeFilter(TreeWalk tw, RevCommit commit)
			throws IOException {
		if (commit != null)
			tw.addTree(commit.getTree());
		else
			tw.addTree(new EmptyTreeIterator());
	}

	private static AbbreviatedObjectId getAbbreviatedObjectId(RevCommit commit) {
		if (commit != null)
			return AbbreviatedObjectId.fromObjectId(commit);
		else
			return ZERO_ID;
	}

	static void calculateAndSetChangeKind(final int direction, Change change) {
		if (ZERO_ID.equals(change.objectId)) { // missing locally
