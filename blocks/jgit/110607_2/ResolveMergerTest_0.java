	@Theory
	public void checkContentMergeConflictBinary(MergeStrategy strategy)
		throws Exception {
		Git git = Git.wrap(db);

		String orig = "1\n2\n3\0";
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
		assertEquals(orig
	}

