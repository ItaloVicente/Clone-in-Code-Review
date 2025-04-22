		assertEquals(new GerritChange(65510, 6), FetchGerritChangePage
				.fromString("refs/changes/10/65510/6"));
		assertEquals(new GerritChange(65510), FetchGerritChangePage
				.fromString("refs/changes/10/65510/"));
		assertEquals(new GerritChange(65510), FetchGerritChangePage
				.fromString("refs/changes/10/65510"));
