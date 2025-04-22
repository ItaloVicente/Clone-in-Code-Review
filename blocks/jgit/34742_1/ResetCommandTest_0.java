	@Test(expected = IllegalStateException.class)
	public void testResetNoPathNoMode() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		git.reset().call();
	}

