
	@Test
	public void commitAfterSquashMerge() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		checkoutBranch("refs/heads/branch1");

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/master");

		MergeResult result = git.merge().include(db.getRef("branch1"))
				.setSquash(true).call();

		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertEquals(MergeResult.MergeStatus.FAST_FORWARD_SQUASHED
				result.getMergeStatus());

		RevCommit squashedCommit = git.commit().call();

		assertEquals(1
		assertNull(db.readSquashCommitMsg());
		assertEquals("commit: Squashed commit of the following:"
				.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("commit: Squashed commit of the following:"
				.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}
