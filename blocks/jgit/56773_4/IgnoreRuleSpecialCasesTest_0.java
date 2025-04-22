
		assertMatch("?"
		assertMatch("*"

		assertMatch("\\["
	}

	@Test
	public void testBracketsUnmatched1() throws Exception {
		assertMatch("["
		assertMatch("[*"
		assertMatch("*["
		assertMatch("*["
		assertMatch("[a]["
		assertMatch("*["
		assertMatch("[a"
		assertMatch("[*"
		assertMatch("[*a"
	}

	@Test
	public void testBracketsUnmatched2() throws Exception {
		assertMatch("*]"
		assertMatch("]a"
		assertMatch("]*"
		assertMatch("]*a"

		assertMatch("]"
		assertMatch("]*"
		assertMatch("]*"
		assertMatch("*]"
		assertMatch("*]"
