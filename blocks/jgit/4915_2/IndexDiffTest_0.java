	@Test
	public void testSubmoduleOnlyInWorkingDirectory() throws Exception {
		File submodule = new File(db.getWorkTree()
				+ Constants.DOT_GIT);
		submodule.mkdirs();
		assertTrue(submodule.isDirectory());
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		assertFalse(diff.diff());
	}

