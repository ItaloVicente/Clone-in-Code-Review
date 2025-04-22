		try (ObjectReader reader = db.newObjectReader()) {
			final AbstractTreeIterator sub = top.createSubtreeIterator(reader);
			assertTrue(sub instanceof FileTreeIterator);
			final FileTreeIterator subfti = (FileTreeIterator) sub;
			assertTrue(sub.first());
			assertFalse(sub.eof());
			assertEquals(paths[2]
			assertEquals(paths[2].length()
			assertEquals(mtime[2]

			sub.next(1);
			assertTrue(sub.eof());
			top.next(1);
			assertFalse(top.first());
			assertFalse(top.eof());
			assertEquals(FileMode.REGULAR_FILE.getBits()
			assertEquals(paths[3]
			assertEquals(paths[3].length()
			assertEquals(mtime[3]

			top.next(1);
			assertTrue(top.eof());
		}
