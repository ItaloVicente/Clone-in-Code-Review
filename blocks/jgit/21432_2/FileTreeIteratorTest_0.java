	@Test
	public void testGetEntryContentLength() throws Exception {
		final FileTreeIterator fti = new FileTreeIterator(db);
		fti.next(1);
		assertEquals(3
		fti.back(1);
		assertEquals(2
		fti.next(1);
		assertEquals(3
		fti.reset();
		assertEquals(2
	}

