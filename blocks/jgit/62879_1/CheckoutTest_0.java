
	@Test
	public void testCheckouSingleFile() throws Exception {
		Git git = new Git(db);
		File a = writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit file a").call();
		writeTrashFile("a"
		assertEquals("[]"
		assertEquals("file a"
	}

	@Test
	public void testCheckoutLink() throws Exception {
		Git git = new Git(db);
		writeLink("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit link a").call();
		writeTrashFile("a"
		assertEquals("[]"
	}
