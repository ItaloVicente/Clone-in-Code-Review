	@Test
	public void knownBrokenTestFileOverlapsTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor().setCheckNameConflicts(false);
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("a/b"));
		editor.finish();

		assertEquals(2
		assertEquals("a"
		assertEquals(FileMode.REGULAR_FILE

		assertEquals("a/b"
		assertEquals(FileMode.REGULAR_FILE

		try (InMemoryRepository git = new InMemoryRepository(
				new DfsRepositoryDescription("test"))) {
			ObjectId rootId;
			try (ObjectInserter ins = git.newObjectInserter()) {
				rootId = dc.writeTree(ins);
				ins.flush();
			}

			try (ObjectReader reader = git.newObjectReader()) {
				byte[] raw = reader.open(rootId).getCachedBytes();
				try {
					new ObjectChecker().checkTree(raw);
					fail("ObjectChecker accepts invalid tree");
				} catch (CorruptObjectException err) {
					assertEquals("duplicate entry names"
				}
			}
		}
	}

	@Test
	public void testFileOverlapsTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor().setCheckNameConflicts(true);
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("a/b"));
		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("a a/b"
			assertEquals("a"
			assertEquals("a/b"
		}

		editor = dc.editor().setCheckNameConflicts(true);
		editor.add(new AddEdit("A.c"));
		editor.add(new AddEdit("A/c"));
		editor.add(new AddEdit("A0c"));
		editor.add(new AddEdit("A"));
		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("A A/c"
			assertEquals("A"
			assertEquals("A/c"
		}

		editor = dc.editor().setCheckNameConflicts(true);
		editor.add(new AddEdit("A.c"));
		editor.add(new AddEdit("A/b/c/d"));
		editor.add(new AddEdit("A/b/c"));
		editor.add(new AddEdit("A0c"));
		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("A/b/c A/b/c/d"
			assertEquals("A/b/c"
			assertEquals("A/b/c/d"
		}
	}

