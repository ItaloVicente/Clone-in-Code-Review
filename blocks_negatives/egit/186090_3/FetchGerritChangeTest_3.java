		assertEquals(Change.create(65510, 6), FetchGerritChangePage
				.determineChangeFromString("refs/changes/10/65510/6"));
		assertEquals(Change.create(65510), FetchGerritChangePage
				.determineChangeFromString("refs/changes/10/65510/"));
		assertEquals(Change.create(65510), FetchGerritChangePage
				.determineChangeFromString("refs/changes/10/65510"));
