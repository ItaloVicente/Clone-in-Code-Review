	@Theory
	public void fileBecomesDir_noTree(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		writeTrashFile("side"
		git.add().addFilepattern("file").addFilepattern("side").call();
		RevCommit first = git.commit().setMessage("base").call();

		writeTrashFile("side"
		RevCommit ours = git.commit().setAll(true)
				.setMessage("ours").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("theirs").call();
		deleteTrashFile("file");
		writeTrashFile("file/file"
		git.add().addFilepattern("file/file").call();

		RevCommit theirs = git.commit().setAll(true)
				.setMessage("theirs").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ResolveMerger merger =
					(ResolveMerger) strategy.newMerger(ins
			boolean success = merger.merge(ours
			assertTrue(success);
			assertTrue(merger.getModifiedFiles().isEmpty());
		}
	}

