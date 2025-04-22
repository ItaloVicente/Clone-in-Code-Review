
	@Theory
	public void testSubmoduleReplacedByMovedFile(IgnoreSubmoduleMode mode)
			throws Exception {
		Git git = Git.wrap(db);
		git.rm().setCached(true).addFilepattern("modules/submodule").call();
		recursiveDelete(submodule_trash);
		JGitTestUtil.deleteTrashFile(db
		writeTrashFile("modules/submodule/fileInRoot"
		git.rm().addFilepattern("fileInRoot").addFilepattern("modules/").call();
		git.add().addFilepattern("modules/").call();
		IndexDiff indexDiff = new IndexDiff(db
				new FileTreeIterator(db));
		indexDiff.setIgnoreSubmoduleMode(mode);
		assertTrue(indexDiff.diff());
		String[] removed = indexDiff.getRemoved().toArray(new String[0]);
		Arrays.sort(removed);
		if (IgnoreSubmoduleMode.ALL.equals(mode)) {
			assertArrayEquals(new String[] { "fileInRoot" }
		} else {
			assertArrayEquals(
					new String[] { "fileInRoot"
					removed);
		}
		assertEquals("[modules/submodule/fileInRoot]"
				indexDiff.getAdded().toString());
	}

