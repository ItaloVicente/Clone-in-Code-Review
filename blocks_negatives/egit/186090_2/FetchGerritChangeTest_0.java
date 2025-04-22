		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("65510"));
		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("/65510"));
		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("65510/"));
		assertEquals(Change.create(65510),
				FetchGerritChangePage.determineChangeFromString("/65510/"));
