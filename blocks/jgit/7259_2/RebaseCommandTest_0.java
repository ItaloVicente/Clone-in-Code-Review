	@Test
	public void testRebaseWithUncommittedDelete() throws Exception {
		File file0 = writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/topic");
		git.rm().addFilepattern("file0").call();

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.FAST_FORWARD
		assertFalse("File should still be deleted"
		assertEquals("[file1
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

