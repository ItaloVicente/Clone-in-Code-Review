		assertEquals(Change.create(65510, 6),
				FetchGerritChangePage.determineChangeFromString("65510/6"));
		assertEquals(Change.create(65510, 6),
				FetchGerritChangePage.determineChangeFromString("/65510/6"));
		assertEquals(Change.create(65510, 6),
				FetchGerritChangePage.determineChangeFromString("65510/6/"));
		assertEquals(Change.create(65510, 6),
				FetchGerritChangePage.determineChangeFromString("/65510/6/"));
		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("10/65510"));
		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("10/65510/"));
		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("/10/65510"));
		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("/10/65510/"));
		assertEquals(Change.create(10),
				FetchGerritChangePage.determineChangeFromString("/10/10"));
		assertEquals(Change.create(10, 9),
				FetchGerritChangePage.determineChangeFromString("/10/9"));
