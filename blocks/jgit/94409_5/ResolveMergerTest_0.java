	@Theory
	public void checkMergeEqualTreesInCore_noRepo(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ThreeWayMerger resolveMerger =
					(ThreeWayMerger) strategy.newMerger(ins
			boolean noProblems = resolveMerger.merge(masterCommit
			assertTrue(noProblems);
		}
	}

