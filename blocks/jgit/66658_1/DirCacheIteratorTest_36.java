		try (final TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(i);
			tw.setRecursive(false);
			int pathIdx = 0;
			while (tw.next()) {
				assertSame(i
				assertEquals(expModes[pathIdx].getBits()
				assertSame(expModes[pathIdx]
				assertEquals(expPaths[pathIdx]

				if (expPos[pathIdx] >= 0) {
					assertEquals(expPos[pathIdx]
					assertSame(ents[expPos[pathIdx]]
				} else {
					assertSame(FileMode.TREE
				}

				pathIdx++;
