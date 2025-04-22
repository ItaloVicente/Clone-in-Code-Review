
	@Test
	public void testFastForwardWithMultipleCommitsOnDifferentBranches()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		createBranch(first

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		createBranch(second

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master")
				.call();

		checkoutBranch("refs/heads/side");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit fourth = git.commit().setMessage("update file2 on side")
				.call();

		checkoutBranch("refs/heads/master");
		MergeResult result = git.merge().include(fourth.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		checkoutBranch("refs/heads/topic");
		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertTrue(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals(Status.FAST_FORWARD
	}
