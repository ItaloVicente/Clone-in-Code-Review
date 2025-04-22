	@Test
	public void testFileOverlapsTree() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor editor = dc.editor();
		editor.add(new AddEdit("a"));
		editor.add(new AddEdit("a/b"));
		editor.finish();

		DirCacheEntry a = dc.getEntry(0);
		DirCacheEntry ab = dc.getEntry(1);
		assertEquals("a"
		assertEquals("a/b"
		fail("This test should not have passed.");
	}

