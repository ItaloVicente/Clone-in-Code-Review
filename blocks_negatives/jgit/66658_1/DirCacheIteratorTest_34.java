		final TreeWalk tw = new TreeWalk(db);
		tw.addTree(i);
		int pathIdx = 0;
		while (tw.next()) {
			assertSame(i, tw.getTree(0, DirCacheIterator.class));
			assertEquals(pathIdx, i.ptr);
			assertSame(ents[pathIdx], i.getDirCacheEntry());
			assertEquals(paths[pathIdx], tw.getPathString());
			assertEquals(modes[pathIdx].getBits(), tw.getRawMode(0));
			assertSame(modes[pathIdx], tw.getFileMode(0));
			pathIdx++;
