		final TreeWalk tw = new TreeWalk(db);
		tw.addTree(i);
		tw.setRecursive(true);
		int pathIdx = 0;
		while (tw.next()) {
			final DirCacheIterator c = tw.getTree(0, DirCacheIterator.class);
			assertNotNull(c);
			assertEquals(pathIdx, c.ptr);
			assertSame(ents[pathIdx], c.getDirCacheEntry());
			assertEquals(paths[pathIdx], tw.getPathString());
			assertEquals(mode.getBits(), tw.getRawMode(0));
			assertSame(mode, tw.getFileMode(0));
			pathIdx++;
