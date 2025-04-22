
	@Test
	public void indexFilesNotIncluded() throws Exception {
		String indexedPath = "indexed.txt";
		String nonIndexedPath = "non-indexed.txt";
		File indexedFile = writeTrashFile(indexedPath
		git.add().addFilepattern(indexedPath).call();
		File nonIndexedFile = writeTrashFile(nonIndexedPath

		git.stashCreate().setIncludeUntracked(true).setKeepIndex(true).call();

		assertFalse("non-indexed file should be stashed."
				nonIndexedFile.exists());
		assertTrue("indexed file should left untouched."

		assertEquals("content2"

		Status status = git.status().addPath(indexedPath).call();
		assertTrue(status.getAdded().contains(indexedPath));
	}

	@Test
	public void indexNotIncluded() throws Exception {
		String indexedPath = "indexed.txt";
		writeTrashFile(indexedPath
		git.add().addFilepattern(indexedPath).call();
		writeTrashFile(indexedPath

		git.stashCreate().setIncludeUntracked(true).setKeepIndex(true).call();

		assertEquals("indexed content"

		Status status = git.status().addPath(indexedPath).call();
		assertTrue(status.getAdded().contains(indexedPath));
	}
