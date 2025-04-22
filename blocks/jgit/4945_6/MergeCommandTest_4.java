	@Test
	public void testNoFastForwardNoCommit() throws Exception {
		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();
		createBranch(initialCommit
		RevCommit secondCommit = git.commit().setMessage("second commit")
				.call();
		checkoutBranch("refs/heads/branch1");

		MergeCommand merge = git.merge();
		merge.setFastForward(FastForwardMode.NO_FF);
		merge.include(db.getRef(Constants.MASTER));
		merge.setCommit(false);
		MergeResult result = merge.call();

		assertEquals(MergeStatus.MERGED_NOT_COMMITTED
		assertEquals(2
		assertEquals(initialCommit
		assertEquals(secondCommit
		assertNull(result.getNewHead());
		assertEquals(RepositoryState.MERGING_RESOLVED
	}

