	@Test
	public void testChangeRefs() {
		assertEquals(Change.create(65510, 6), FetchGerritChangePage
				.determineChangeFromString("refs/changes/10/65510/6"));
		assertNull(FetchGerritChangePage
				.determineChangeFromString("refs/changes/10/65510/"));
		assertNull(FetchGerritChangePage
				.determineChangeFromString("refs/changes/10/"));
		assertEquals(Change.create(65510, 6), FetchGerritChangePage
				.determineChangeFromString("refs/changes/42/65510/6"));
	}

	@Test
	public void testFromRef() {
		assertNull(Change.fromRef("refs/changes/42/65510/6"));
		assertNull(Change.fromRef("refs/changes/10/65510/6..7"));
		assertNull(Change.fromRef("refs/changes/10/65510/6/7"));
		assertEquals(Change.create(65510, 6),
				Change.fromRef("refs/changes/10/65510/6"));
	}
