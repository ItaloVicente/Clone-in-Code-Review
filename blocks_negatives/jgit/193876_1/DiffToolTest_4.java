	private RevCommit createStagedChanges() throws Exception {
		RevCommit commit = createUnstagedChanges();
		git.add().addFilepattern(".").call();
		return commit;
	}

	private List<DiffEntry> getRepositoryChanges(RevCommit commit)
			throws Exception {
		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit.getTree());
		FileTreeIterator modifiedTree = new FileTreeIterator(db);
		tw.addTree(modifiedTree);
		List<DiffEntry> changes = DiffEntry.scan(tw);
		return changes;
	}

	private String[] getExpectedDiffToolOutput(List<DiffEntry> changes) {
		String[] expectedToolOutput = new String[changes.size()];
		for (int i = 0; i < changes.size(); ++i) {
			DiffEntry change = changes.get(i);
			String newPath = change.getNewPath();
			String oldPath = change.getOldPath();
			String newIdName = change.getNewId().name();
			String oldIdName = change.getOldId().name();
			String expectedLine = "M\t" + newPath + " (" + newIdName + ")"
					+ "\t" + oldPath + " (" + oldIdName + ")";
