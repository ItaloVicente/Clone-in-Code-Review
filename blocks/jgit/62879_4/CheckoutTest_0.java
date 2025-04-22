
	@Test
	public void testCheckouSingleFile() throws Exception {
		try (Git git = new Git(db)) {
			File a = writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit file a").call();
			writeTrashFile("a"
			assertEquals("b"
			assertEquals("[]"
			assertEquals("file a"
		}
	}

	@Test
	public void testCheckoutLink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		try (Git git = new Git(db)) {
			Path path = writeLink("a"
			assertTrue(Files.isSymbolicLink(path));
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit link a").call();
			deleteTrashFile("a");
			writeTrashFile("a"
			assertFalse(Files.isSymbolicLink(path));
			assertEquals("[]"
			assertEquals("link_a"
			assertTrue(Files.isSymbolicLink(path));
		}
	}
