	@Theory
	public void testMergeSuccessAllStrategiesNoCommit(
			MergeStrategy mergeStrategy) throws Exception {
		Git git = new Git(db);

		RevCommit first = git.commit().setMessage("first").call();
		createBranch(first

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("second").call();

		checkoutBranch("refs/heads/side");
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		RevCommit thirdCommit = git.commit().setMessage("third").call();

		MergeResult result = git.merge().setStrategy(mergeStrategy)
				.setCommit(false)
				.include(db.getRef(Constants.MASTER)).call();
		assertEquals(MergeStatus.MERGED_NOT_COMMITTED
		assertEquals(db.getRef(Constants.HEAD).getTarget().getObjectId()
				thirdCommit.getId());
	}

