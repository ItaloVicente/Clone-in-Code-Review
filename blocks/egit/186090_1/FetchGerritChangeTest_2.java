		assertEquals(new GerritChange(65510, 6),
				FetchGerritChangePage.fromString("10/65510/6"));
		assertEquals(new GerritChange(65510, 6),
				FetchGerritChangePage.fromString("/10/65510/6"));
		assertEquals(new GerritChange(65510, 6),
				FetchGerritChangePage.fromString("10/65510/6/"));
		assertEquals(new GerritChange(65510, 6), FetchGerritChangePage
				.fromString("/10/65510/6/"));
		assertEquals(new GerritChange(10, 6),
				FetchGerritChangePage.fromString("/10/10/6"));
		assertEquals(new GerritChange(10, 6),
				FetchGerritChangePage.fromString("/65510/10/6"));
