
	public void testRemovedUntracked() throws Exception{
		Git git = new Git(db);
		String path = "file";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		git.commit().setMessage("commit").call();
		removeFromIndex(path);
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertTrue(diff.getRemoved().contains(path));
		assertTrue(diff.getUntracked().contains(path));
	}

	private void removeFromIndex(String path) throws IOException {
		final DirCache dirc = db.lockDirCache();
		final int first = dirc.findEntry(path);
		if (first < 0)
			throw new IOException("could not find entry " + path);
		final DirCacheBuilder edit = dirc.builder();
		if (first > 0)
			edit.keep(0
		final int next = dirc.nextEntry(first);
		if (next < dirc.getEntryCount())
			edit.keep(next
		if (!edit.commit())
			throw new IOException("could not commit");
	}
