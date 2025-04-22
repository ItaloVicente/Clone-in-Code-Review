	@Test
	public void testAdded2() throws IOException {
		writeTrashFile("f"
		writeTrashFile("d/f"
		DirCache index = db.lockDirCache();
		DirCacheEditor editor = index.editor();
		editor.add(add(db
		editor.commit();
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertEquals(1
		assertTrue(diff.getAdded().contains("f"));
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertTrue(diff.getUntrackedFolders().contains("d"));
	}

	@Test
	public void testAdded3() throws IOException {
		writeTrashFile("f"
		writeTrashFile("d/f"
		DirCache index = db.lockDirCache();
		DirCacheEditor editor = index.editor();
		editor.add(add(db
		editor.commit();
		DiffFormatter diffFormatter = new DiffFormatter(System.out);
		diffFormatter.setRepository(db);
		DirCacheIterator oldTree = new DirCacheIterator(db.readDirCache());
		FileTreeIterator newTree = new FileTreeIterator(db);
		List<DiffEntry> scan = diffFormatter.scan(oldTree
		assertEquals(0
	}

