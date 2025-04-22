	@Test
	public void testCheckoutForcedNoChangeNotInIndex() throws Exception {
		git.checkout().setCreateBranch(true).setName("test2").call();
		File f = writeTrashFile("NewFile.txt"
		git.add().addFilepattern("NewFile.txt").call();
		git.commit().setMessage("New file created").call();
		git.checkout().setName("test").call();
		assertFalse("NewFile.txt should not exist"
		writeTrashFile("NewFile.txt"
		git.add().addFilepattern("NewFile.txt").call();
		git.commit().setMessage("New file created again with same content")
				.call();
		git.rm().addFilepattern("NewFile.txt").setCached(true).call();
		assertTrue("NewFile.txt should exist"
		git.checkout().setForced(true).setName("test2").call();
		assertTrue("NewFile.txt should exist"
		assertEquals(Constants.R_HEADS + "test2"
				.exactRef(Constants.HEAD).getTarget().getName());
		assertTrue("Force checkout should have undone git rm --cached"
				git.status().call().isClean());
	}

	@Test
	public void testCheckoutNoChangeNotInIndex() throws Exception {
		git.checkout().setCreateBranch(true).setName("test2").call();
		File f = writeTrashFile("NewFile.txt"
		git.add().addFilepattern("NewFile.txt").call();
		git.commit().setMessage("New file created").call();
		git.checkout().setName("test").call();
		assertFalse("NewFile.txt should not exist"
		writeTrashFile("NewFile.txt"
		git.add().addFilepattern("NewFile.txt").call();
		git.commit().setMessage("New file created again with same content")
				.call();
		git.rm().addFilepattern("NewFile.txt").setCached(true).call();
		assertTrue("NewFile.txt should exist"
		git.checkout().setName("test2").call();
		assertTrue("NewFile.txt should exist"
		assertEquals(Constants.R_HEADS + "test2"
				.exactRef(Constants.HEAD).getTarget().getName());
		org.eclipse.jgit.api.Status status = git.status().call();
		assertEquals("[NewFile.txt]"
		assertEquals("[NewFile.txt]"
	}

