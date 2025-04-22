	@Theory
	public void checkMergeCrissCross(MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("1"
		git.add().addFilepattern("1").call();
		RevCommit first = git.commit().setMessage("added 1").call();

		writeTrashFile("1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified 1 on master").call();

		writeTrashFile("1"
		git.commit().setAll(true)
				.setMessage("modified 1 on master again").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("1"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified 1 on side").call();

		writeTrashFile("1"
		git.commit().setAll(true)
				.setMessage("modified 1 on side again").call();

		MergeResult result = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		result.getNewHead();
		git.checkout().setName("master").call();
		result = git.merge().setStrategy(strategy).include(sideCommit).call();
		assertEquals(MergeStatus.MERGED

		try {
			MergeResult mergeResult = git.merge().setStrategy(strategy)
					.include(git.getRepository().getRef("refs/heads/side"))
					.call();
			assertEquals(MergeStrategy.RECURSIVE
			assertEquals(MergeResult.MergeStatus.MERGED
					mergeResult.getMergeStatus());
			assertEquals("1master2\n2\n3side2"
		} catch (JGitInternalException e) {
			assertEquals(MergeStrategy.RESOLVE
		}
	}

