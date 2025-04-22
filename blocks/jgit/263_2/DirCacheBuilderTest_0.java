	public void testBuildRejectsUnsetFileMode() throws Exception {
		final DirCache dc = DirCache.newInCore();
		final DirCacheBuilder b = dc.builder();
		assertNotNull(b);

		final DirCacheEntry e = new DirCacheEntry("a");
		assertEquals(0
		try {
			b.add(e);
		} catch (IllegalArgumentException err) {
			assertEquals("FileMode not set for path a"
		}
	}

