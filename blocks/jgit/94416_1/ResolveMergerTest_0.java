	@Theory
	public void checkContentMergeNoConflict(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		git.commit().setAll(true).setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		git.checkout().setName("master").call();
		MergeResult result =
				git.merge().setStrategy(strategy).include(sideCommit).call();
		assertEquals(MergeStatus.MERGED
		String expected = "1master\n2\n3side";
		assertEquals(expected
	}

	@Theory
	public void checkContentMergeConflict(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		git.commit().setAll(true).setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		git.checkout().setName("master").call();
		MergeResult result =
				git.merge().setStrategy(strategy).include(sideCommit).call();
		assertEquals(MergeStatus.CONFLICTING
		String expected = "<<<<<<< HEAD\n"
				+ "1master\n"
				+ "=======\n"
				+ "1side\n"
				+ ">>>>>>> " + sideCommit.name() + "\n"
				+ "2\n"
				+ "3";
		assertEquals(expected
	}

