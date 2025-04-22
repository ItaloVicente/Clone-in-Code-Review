	@Test
	public void testDirModeAndRegex1() {
		Boolean assume = useOldRule;

		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	@Test
	public void testDirModeAndRegex2() {
		Boolean assume = useOldRule;

		String pattern = "a/[a-b]/src/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	@Test
	public void testDirModeAndRegex3() {
		Boolean assume = useOldRule;

		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

