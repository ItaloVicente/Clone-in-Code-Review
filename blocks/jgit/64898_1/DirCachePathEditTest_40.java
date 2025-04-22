	@Test
	public void testFileReplacesTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("b/c"));
		editor.add(new AddEdit("b/d"));
		editor.add(new AddEdit("e"));
		editor.finish();

		editor = dc.editor();
		editor.add(new AddEdit("b"));
		editor.finish();

		assertEquals(3
		assertEquals("a"
		assertEquals("b"
		assertEquals("e"

		dc.clear();
		editor = dc.editor();
		editor.add(new AddEdit("A.c"));
		editor.add(new AddEdit("A/c"));
		editor.add(new AddEdit("A0c"));
		editor.finish();

		editor = dc.editor();
		editor.add(new AddEdit("A"));
		editor.finish();
		assertEquals(3
		assertEquals("A"
		assertEquals("A.c"
		assertEquals("A0c"
	}

	@Test
	public void testTreeReplacesFile() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("ab"));
		editor.add(new AddEdit("b"));
		editor.add(new AddEdit("e"));
		editor.finish();

		editor = dc.editor();
		editor.add(new AddEdit("b/c/d/f"));
		editor.add(new AddEdit("b/g/h/i"));
		editor.finish();

		assertEquals(5
		assertEquals("a"
		assertEquals("ab"
		assertEquals("b/c/d/f"
		assertEquals("b/g/h/i"
		assertEquals("e"
	}

	@Test
	public void testDuplicateFiles() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("a"));

		try {
			editor.finish();
			fail("Expected DirCacheNameConflictException to be thrown");
		} catch (DirCacheNameConflictException e) {
			assertEquals("a a"
			assertEquals("a"
			assertEquals("a"
		}
	}

	@Test
	public void testFileOverlapsTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("a/b").setReplace(false));
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
		editor.add(new AddEdit("A/c").setReplace(false));
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
		editor.add(new AddEdit("A/b/c/d").setReplace(false));
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

