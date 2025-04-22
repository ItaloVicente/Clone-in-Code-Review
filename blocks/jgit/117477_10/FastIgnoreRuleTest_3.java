	@Test
	public void testPathMatch() {
		pathMatch = true;
		assertMatched("a"
		assertMatched("a/"
		assertNotMatched("a/"

		assertMatched("**"
		assertMatched("**"
		assertMatched("**"


	}

