		boolean hasChanges = indexDiff.diff();
		if (mode == IgnoreSubmoduleMode.ALL) {
			assertFalse(hasChanges);
		} else {
			assertTrue(hasChanges);
			assertEquals("[]"
			assertEquals("[]"
			assertEquals("[modules/submodule]"
					indexDiff.getModified().toString());
		}
	}

	@Theory
	public void testSubmoduleReplacedByFileStaged(IgnoreSubmoduleMode mode)
			throws Exception {
		recursiveDelete(submodule_trash);
		writeTrashFile("modules/submodule"
		try (Git git = new Git(db)) {
			git.add().addFilepattern("modules/submodule").call();
		}
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		boolean hasChanges = indexDiff.diff();
		assertTrue(hasChanges);
