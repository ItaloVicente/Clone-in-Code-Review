		DiffRegionFormatter formatter = new DiffRegionFormatter(
				document);
		formatter.setRepository(repository);
		formatter.format(commit.getTree(), commit.getParent(0).getTree());
		assertTrue(document.getLength() > 0);
		DiffRegion[] regions = formatter.getRegions();
		assertNotNull(regions);
		assertTrue(regions.length > 0);
		for (DiffRegion region : regions) {
			assertNotNull(region);
			assertTrue(region.getOffset() >= 0);
			assertTrue(region.getLength() >= 0);
			assertTrue(region.getOffset() < document.getLength());
