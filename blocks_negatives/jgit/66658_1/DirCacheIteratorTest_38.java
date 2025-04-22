		final TreeWalk tw = new TreeWalk(db);
		for (int victimIdx = 0; victimIdx < paths.length; victimIdx++) {
			tw.reset();
			tw.addTree(new DirCacheIterator(dc));
			tw.setFilter(PathFilterGroup.createFromStrings(Collections
					.singleton(paths[victimIdx])));
			tw.setRecursive(tw.getFilter().shouldBeRecursive());
			assertTrue(tw.next());
			final DirCacheIterator c = tw.getTree(0, DirCacheIterator.class);
			assertNotNull(c);
			assertEquals(victimIdx, c.ptr);
			assertSame(ents[victimIdx], c.getDirCacheEntry());
			assertEquals(paths[victimIdx], tw.getPathString());
			assertEquals(mode.getBits(), tw.getRawMode(0));
			assertSame(mode, tw.getFileMode(0));
			assertFalse(tw.next());
