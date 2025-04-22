	@Test
	public void testDollar() throws Exception {
		assertMatch("$"
		assertMatch("$x"
		assertMatch("$x"
		assertMatch("$x"

		assertMatch("$x.*"
		assertMatch("*$"
		assertMatch("*.$"

		assertMatch("$*x"
		assertMatch("x*$"
		assertMatch("x*$"
		assertMatch("[a$b]"
	}

	@Test
	public void testCaret() throws Exception {
		assertMatch("^"
		assertMatch("^x"
		assertMatch("^x"
		assertMatch("^x"

		assertMatch("^x.*"
		assertMatch("*^"
		assertMatch("*.^"

		assertMatch("x*^"
		assertMatch("^*x"
		assertMatch("^*x"
		assertMatch("[a^b]"
	}

