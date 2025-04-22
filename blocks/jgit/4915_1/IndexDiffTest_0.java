	@Test
	public void testSubmoduleOnlyInWorkingDirectory() throws Exception {
		File submodule = new File(db.getWorkTree()
				+ Constants.DOT_GIT);
		submodule.mkdirs();
		assertTrue(submodule.isDirectory());
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertTrue(diff.getAdded().isEmpty());
		assertTrue(diff.getAssumeUnchanged().isEmpty());
		assertTrue(diff.getChanged().isEmpty());
		assertTrue(diff.getConflicting().isEmpty());
		assertTrue(diff.getIgnoredNotInIndex().isEmpty());
		assertTrue(diff.getMissing().isEmpty());
		assertTrue(diff.getModified().isEmpty());
		assertTrue(diff.getRemoved().isEmpty());
		assertTrue(diff.getUntracked().isEmpty());
		assertTrue(diff.getUntrackedFolders().isEmpty());
	}

