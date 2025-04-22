		DiffRegion[] regions = formatter.getRegions();
		assertNotNull(regions);
		assertTrue(regions.length > 0);
		for (DiffRegion region : regions) {
			assertNotNull(region);
			assertNotNull(region.diffType);
			assertTrue(region.getOffset() >= 0);
			assertTrue(region.getLength() >= 0);
			assertTrue(region.getOffset() < document.getLength());
