	private Repository cloneWithoutCloningSubmodule() throws Exception {
		File directory = createTempDirectory(
				"testCloneWithoutCloningSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(false);
		clone.setURI(db.getDirectory().toURI().toString());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		return git2.getRepository();
	}

	@Theory
	public void testCleanAfterClone(IgnoreSubmoduleMode mode) throws Exception {
		Repository db2 = cloneWithoutCloningSubmodule();
		IndexDiff indexDiff = new IndexDiff(db2
				new FileTreeIterator(db2));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertFalse(indexDiff.diff());
	}

	@Theory
	public void testMissingIfDirectoryGone(IgnoreSubmoduleMode mode)
			throws Exception {
		recursiveDelete(submodule_trash);
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		boolean hasChanges = indexDiff.diff();
		if (mode != IgnoreSubmoduleMode.ALL) {
			assertTrue(hasChanges);
			assertEquals("[modules/submodule]"
					indexDiff.getMissing().toString());
		} else {
			assertFalse(hasChanges);
		}
	}

	@Theory
	public void testSubmoduleReplacedByFile(IgnoreSubmoduleMode mode)
			throws Exception {
		recursiveDelete(submodule_trash);
		writeTrashFile("modules/submodule"
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertTrue(indexDiff.diff());
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[modules/submodule]"
	}

