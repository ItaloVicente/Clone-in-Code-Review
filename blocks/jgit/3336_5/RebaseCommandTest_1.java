	@Test
	public void testRebaseFailsCantCherryPickMergeCommits()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		createBranch(first

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit topicCommit = git.commit()
				.setMessage("update file3 on topic").call();

		createBranch(topicCommit

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/side");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit sideCommit = git.commit().setMessage("update file2 on side")
				.call();

		checkoutBranch("refs/heads/topic");
		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		try {
			RebaseResult rebase = git.rebase().setUpstream("refs/heads/master")
					.call();
			fail("MultipleParentsNotAllowedException expected: "
					+ rebase.getStatus());
		} catch (JGitInternalException e) {
		}
	}

	@Test
	public void testRebaseParentOntoHeadShouldBeUptoDate() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit parent = git.commit().setMessage("parent comment").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("head commit").call();

		RebaseResult result = git.rebase().setUpstream(parent).call();
		assertEquals(Status.UP_TO_DATE
	}

