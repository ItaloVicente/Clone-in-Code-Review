
		assertMatch("?"
		assertMatch("*"

		assertMatch("\\["
	}

	@Test
	public void testBracketsIncompatibleBehavior() throws Exception {
		assertMatch("["

		assertMatch("[*"

		assertMatch("*["
		assertMatch("*["
	}

	@Test
	public void testBracketsCompatibleBehavior() throws Exception {
		assertMatch("*["

		assertMatch("[a"

		assertMatch("[*"

		assertMatch("[*a"

		assertMatch("*]"
		assertMatch("]a"
		assertMatch("]*"
		assertMatch("]*a"

		assertMatch("]"
		assertMatch("]*"
		assertMatch("]*"
		assertMatch("*]"
		assertMatch("*]"
