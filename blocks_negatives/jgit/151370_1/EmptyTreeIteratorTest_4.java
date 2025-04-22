		final ObjectReader reader = db.newObjectReader();
		final AbstractTreeIterator sub = etp.createSubtreeIterator(reader);
		assertNotNull(sub);
		assertTrue(sub.first());
		assertTrue(sub.eof());
		assertTrue(sub instanceof EmptyTreeIterator);
