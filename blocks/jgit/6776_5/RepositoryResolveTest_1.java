	@Test
	public void resolveExprSimple() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();
		assertEquals("master"
		assertEquals("refs/heads/master"
		assertEquals("master"
	}

