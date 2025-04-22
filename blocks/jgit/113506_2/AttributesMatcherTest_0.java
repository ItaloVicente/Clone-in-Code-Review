	@Test
	public void testBracketsInGroup() {

		String[] patterns = new String[]{"[[\\]]"
		for (String pattern : patterns) {
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern

			assertMatched(pattern
			assertMatched(pattern
		}

		patterns = new String[]{"[[]]"
		for (String pattern : patterns) {
			assertNotMatched(pattern
			assertMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern
			assertNotMatched(pattern

			assertNotMatched(pattern
			assertNotMatched(pattern
		}
	}

