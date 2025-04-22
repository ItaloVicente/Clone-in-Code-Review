
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
		final DirCacheEditor edit = dirc.editor();
		edit.add(new DirCacheEditor.DeletePath(path));
		if (!edit.commit())
			throw new IOException("could not commit");
	}
