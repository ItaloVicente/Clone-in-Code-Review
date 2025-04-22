	@Theory
	public void checkContentMergeNoConflict_noRepo(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ResolveMerger merger =
					(ResolveMerger) strategy.newMerger(ins
			boolean noProblems = merger.merge(masterCommit
			assertTrue(noProblems);
			assertEquals("1master\n2\n3side"
					readBlob(merger.getResultTreeId()
		}
	}

