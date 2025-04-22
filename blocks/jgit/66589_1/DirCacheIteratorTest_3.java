		try (final TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new DirCacheIterator(dc));
			tw.setRecursive(true);
			int pathIdx = 0;
			while (tw.next()) {
				final DirCacheIterator c = tw.getTree(0
				assertNotNull(c);
				assertEquals(pathIdx
				assertSame(ents[pathIdx]
				assertEquals(paths[pathIdx]
				assertEquals(mode.getBits()
				assertSame(mode
				pathIdx++;
			}
			assertEquals(paths.length
