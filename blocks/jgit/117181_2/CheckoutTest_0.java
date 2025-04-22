
	@Test
	public void testCheckoutForce_Bug530771() throws Exception {
		try (Git git = new Git(db)) {
			File f = writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("create a").call();
			writeTrashFile("a"
			assertEquals("[]"
					Arrays.toString(execute("git checkout -f -F HEAD")));
			assertEquals("Hello world"
			assertEquals("[a
					indexState(db
		}
	}
