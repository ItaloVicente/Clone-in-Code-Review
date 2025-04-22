		assertFalse(dc.hasUnmergedPaths());
	}

	public void testDetectUnmergedPaths() throws Exception {
		final DirCache dc = DirCache.read(db);
		final DirCacheEntry[] ents = new DirCacheEntry[3];

		ents[0] = new DirCacheEntry("a"
		ents[0].setFileMode(FileMode.REGULAR_FILE);
		ents[1] = new DirCacheEntry("a"
		ents[1].setFileMode(FileMode.REGULAR_FILE);
		ents[2] = new DirCacheEntry("a"
		ents[2].setFileMode(FileMode.REGULAR_FILE);

		final DirCacheBuilder b = dc.builder();
		for (int i = 0; i < ents.length; i++)
			b.add(ents[i]);
		b.finish();
		assertTrue(dc.hasUnmergedPaths());
