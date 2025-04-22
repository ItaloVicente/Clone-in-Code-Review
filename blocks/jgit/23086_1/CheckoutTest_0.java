
	@Test
	public void testCheckoutPath() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit file a").call();
		git.branchCreate().setName("branch_1").call();
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("commit file b").call();
		File a = writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("modified a").call();
		assertArrayEquals(new String[] { "" }
				execute("git checkout HEAD~2 -- a"));
		assertEquals("Hello world a"
	}
