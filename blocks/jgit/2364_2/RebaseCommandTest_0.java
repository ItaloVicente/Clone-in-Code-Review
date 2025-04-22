		assertTrue(new File(db.getWorkTree()
		checkFile(file2
		assertEquals(Status.FAST_FORWARD
	}

	@Test
	public void testFastForwardWithMultipleCommits() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		File file2 = writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Change content of file2").call();

		checkoutBranch("refs/heads/topic");
		assertFalse(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertTrue(new File(db.getWorkTree()
		checkFile(file2
		assertEquals(Status.FAST_FORWARD
