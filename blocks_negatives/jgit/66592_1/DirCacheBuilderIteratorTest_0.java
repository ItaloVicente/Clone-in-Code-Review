		assertTrue("found " + paths[expIdx], tw.next());
		final DirCacheIterator c = tw.getTree(0, DirCacheIterator.class);
		assertNotNull(c);
		assertEquals(expIdx, c.ptr);
		assertSame(ents[expIdx], c.getDirCacheEntry());
		assertEquals(paths[expIdx], tw.getPathString());
		assertEquals(mode.getBits(), tw.getRawMode(0));
		assertSame(mode, tw.getFileMode(0));
		b.add(c.getDirCacheEntry());
