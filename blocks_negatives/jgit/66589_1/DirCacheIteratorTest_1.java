		final TreeWalk tw = new TreeWalk(db);
		tw.addTree(i);
		tw.setRecursive(false);
		int pathIdx = 0;
		while (tw.next()) {
			assertSame(i, tw.getTree(0, DirCacheIterator.class));
			assertEquals(expModes[pathIdx].getBits(), tw.getRawMode(0));
			assertSame(expModes[pathIdx], tw.getFileMode(0));
			assertEquals(expPaths[pathIdx], tw.getPathString());

			if (expPos[pathIdx] >= 0) {
				assertEquals(expPos[pathIdx], i.ptr);
				assertSame(ents[expPos[pathIdx]], i.getDirCacheEntry());
			} else {
				assertSame(FileMode.TREE, tw.getFileMode(0));
