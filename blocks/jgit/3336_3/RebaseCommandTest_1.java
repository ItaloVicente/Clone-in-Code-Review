	@Test
	public void testRebaseFailsCantCherryPickMergeCommits()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		createBranch(first
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit topicCommit = git.commit()
				.setMessage("update file3 on topic").call();

		checkoutBranch("refs/heads/master");
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
		git.commit().setMessage("update file2 on side")
				.call();

		checkoutBranch("refs/heads/master");
		MergeResult result = git.merge().include(topicCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		try {
			git.rebase().setUpstream("refs/heads/topic").call();
			fail("MultipleParentsNotAllowedException expected");
		} catch (JGitInternalException e) {
		}
	}

