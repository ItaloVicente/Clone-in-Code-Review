	private List<DiffEntry> diffIndexAgainstWorking(final RevCommit commit)
			throws IOException {
		TreeWalk walk = createTreeWalk();
		try {
			walk.addTree(commit.getParent(1).getTree());
			walk.addTree(commit.getTree());
			return DiffEntry.scan(walk);
		} finally {
			walk.release();
		}
	}

