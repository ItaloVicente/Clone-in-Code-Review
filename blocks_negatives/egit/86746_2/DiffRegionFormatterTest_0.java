		DiffStyleRange[] ranges = formatter.getRanges();
		assertNotNull(ranges);
		assertTrue(ranges.length > 0);
		for (DiffStyleRange range : ranges) {
			assertNotNull(range);
			assertNotNull(range.diffType);
			assertTrue(range.start >= 0);
			assertTrue(range.length >= 0);
			assertTrue(range.start < document.getLength());
