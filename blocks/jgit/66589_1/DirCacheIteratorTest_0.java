		try (final TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(i);
			int pathIdx = 0;
			while (tw.next()) {
				assertSame(i
				assertEquals(pathIdx
				assertSame(ents[pathIdx]
				assertEquals(paths[pathIdx]
				assertEquals(modes[pathIdx].getBits()
				assertSame(modes[pathIdx]
				pathIdx++;
			}
			assertEquals(paths.length
