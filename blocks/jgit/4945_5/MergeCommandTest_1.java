	@Test
	public void testFastForwardNoCommit() throws Exception {
		Git git = new Git(db);
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first

		RevCommit second = git.commit().setMessage("second commit").call();

		checkoutBranch("refs/heads/branch1");

		MergeResult result = git.merge().include(db.getRef(Constants.MASTER))
				.setCommit(false).call();

		assertEquals(MergeResult.MergeStatus.FAST_FORWARD
				result.getMergeStatus());
		assertEquals(second
		assertEquals("merge refs/heads/master: Fast-forward"
				.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("merge refs/heads/master: Fast-forward"
				.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}

