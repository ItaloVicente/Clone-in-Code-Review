			assertTrue("found " + paths[expIdx]
			final DirCacheIterator c = tw.getTree(0
			assertNotNull(c);
			assertEquals(expIdx
			assertSame(ents[expIdx]
			assertEquals(paths[expIdx]
			assertEquals(mode.getBits()
			assertSame(mode
			b.add(c.getDirCacheEntry());
