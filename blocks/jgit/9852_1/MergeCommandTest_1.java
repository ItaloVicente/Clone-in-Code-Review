	@Test
	public void testFastForwardOnly() throws Exception {
		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();
		createBranch(initialCommit
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");

		MergeCommand merge = git.merge();
		merge.setFastForward(FastForwardMode.FF_ONLY);
		merge.include(db.getRef(Constants.MASTER));
		MergeResult result = merge.call();

		assertEquals(MergeStatus.FAST_FORWARD
	}
