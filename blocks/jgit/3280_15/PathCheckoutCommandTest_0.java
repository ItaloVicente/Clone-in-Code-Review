
	@Test
	public void testUpdateWorkingDirectoryFromIndex() throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		written = writeTrashFile("Test.txt"
		assertEquals(""
		co.addPath("Test.txt").call();
		assertEquals("3a"
		assertEquals("c"
	}

	@Test
	public void testUpdateWorkingDirectoryFromHeadWithIndexChange()
			throws Exception {
		CheckoutCommand co = git.checkout();
		File written = writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		written = writeTrashFile("Test.txt"
		assertEquals(""
		co.addPath("Test.txt").setStartPoint("HEAD").call();
		assertEquals("3"
		assertEquals("c"
	}

