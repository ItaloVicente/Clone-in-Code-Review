	public void testCheckoutAllPaths() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit file a").call();
			git.branchCreate().setName("branch_1").call();
			git.checkout().setName("branch_1").call();
			File b = writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("commit file b").call();
			File a = writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("modified a").call();
			assertArrayEquals(new String[] { "" }
					execute("git checkout HEAD~2 -- ."));
			assertEquals("Hello world a"
			assertArrayEquals(new String[] { "* branch_1"
					execute("git branch"));
			assertEquals("Hello world b"
		}
	}

	@Test
	public void testCheckoutSingleFile() throws Exception {
