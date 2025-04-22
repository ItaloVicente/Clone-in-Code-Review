		try (final TreeWalk tw = new TreeWalk(db)) {
			for (int victimIdx = 0; victimIdx < paths.length; victimIdx++) {
				tw.reset();
				tw.addTree(new DirCacheIterator(dc));
				tw.setFilter(PathFilterGroup.createFromStrings(Collections
						.singleton(paths[victimIdx])));
				tw.setRecursive(tw.getFilter().shouldBeRecursive());
				assertTrue(tw.next());
				final DirCacheIterator c = tw.getTree(0
				assertNotNull(c);
				assertEquals(victimIdx
				assertSame(ents[victimIdx]
				assertEquals(paths[victimIdx]
				assertEquals(mode.getBits()
				assertSame(mode
				assertFalse(tw.next());
			}
