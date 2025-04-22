		assertEquals(Change.create(65510, 6),
				FetchGerritChangePage.determineChangeFromString("10/65510/6"));
		assertEquals(Change.create(65510, 6),
				FetchGerritChangePage.determineChangeFromString("/10/65510/6"));
		assertEquals(Change.create(65510, 6),
				FetchGerritChangePage.determineChangeFromString("10/65510/6/"));
		assertEquals(Change.create(65510, 6), FetchGerritChangePage
				.determineChangeFromString("/10/65510/6/"));
		assertEquals(Change.create(10, 6),
				FetchGerritChangePage.determineChangeFromString("/10/10/6"));
		assertEquals(Change.create(10, 6),
				FetchGerritChangePage.determineChangeFromString("/65510/10/6"));
