		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("65510"));
		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("/65510"));
		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("65510/"));
		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("/65510/"));
