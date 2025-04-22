	@Test
	public void testRemoveCached() throws Exception {
		File newFile = writeTrashFile("new.txt"
		git.add().addFilepattern(newFile.getName()).call();
		assertEquals("[new.txt
				indexState(0));

		git.rm().setCached(true).addFilepattern(newFile.getName()).call();

		assertEquals("[test.txt
		assertTrue("File should not have been removed."
	}
