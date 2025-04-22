		assertEquals(new GerritChange(65510, 6),
				FetchGerritChangePage.fromString("65510/6"));
		assertEquals(new GerritChange(65510, 6),
				FetchGerritChangePage.fromString("/65510/6"));
		assertEquals(new GerritChange(65510, 6),
				FetchGerritChangePage.fromString("65510/6/"));
		assertEquals(new GerritChange(65510, 6),
				FetchGerritChangePage.fromString("/65510/6/"));
		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("10/65510"));
		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("10/65510/"));
		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("/10/65510"));
		assertEquals(new GerritChange(65510),
				FetchGerritChangePage.fromString("/10/65510/"));
		assertEquals(new GerritChange(10),
				FetchGerritChangePage.fromString("/10/10"));
		assertEquals(new GerritChange(10, 9),
				FetchGerritChangePage.fromString("/10/9"));
