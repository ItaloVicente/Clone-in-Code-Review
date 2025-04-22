	@Test
	public void testFileOverlapsTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
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

		editor = dc.editor();
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

		editor = dc.editor();
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

