	@Test
	public void resolveExprSimple() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();
		assertEquals("master"
		assertEquals(c1.getName()
		assertEquals("master"
	}

