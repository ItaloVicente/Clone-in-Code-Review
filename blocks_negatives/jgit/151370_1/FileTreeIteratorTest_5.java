		final ObjectReader reader = db.newObjectReader();
		final AbstractTreeIterator sub = top.createSubtreeIterator(reader);
		assertTrue(sub instanceof FileTreeIterator);
		final FileTreeIterator subfti = (FileTreeIterator) sub;
		assertTrue(sub.first());
		assertFalse(sub.eof());
		assertEquals(paths[2], nameOf(sub));
		assertEquals(paths[2].length(), subfti.getEntryLength());
		assertEquals(mtime[2], subfti.getEntryLastModifiedInstant());

		sub.next(1);
		assertTrue(sub.eof());

		top.next(1);
		assertFalse(top.first());
		assertFalse(top.eof());
		assertEquals(FileMode.REGULAR_FILE.getBits(), top.mode);
		assertEquals(paths[3], nameOf(top));
		assertEquals(paths[3].length(), top.getEntryLength());
		assertEquals(mtime[3], top.getEntryLastModifiedInstant());

		top.next(1);
		assertTrue(top.eof());
