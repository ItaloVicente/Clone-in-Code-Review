	@Test
	public void testFastForwardRebaseWithModification() throws Exception {
		writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		writeTrashFile("file0"

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.FAST_FORWARD
		assertEquals("[file0
				+ "[file1
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

