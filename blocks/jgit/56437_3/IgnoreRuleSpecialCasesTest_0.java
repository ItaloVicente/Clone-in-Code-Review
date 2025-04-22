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

	@Test
	public void testPlus() throws Exception {
		assertMatch("+"
		assertMatch("+x"
		assertMatch("+x"
		assertMatch("+x"
		assertMatch("x+"

		assertMatch("+x.*"
		assertMatch("*+"
		assertMatch("*.+"

		assertMatch("x*+"
		assertMatch("+*x"
		assertMatch("+*x"
		assertMatch("[a+b]"
	}

	@Test
	public void testBrackets() throws Exception {
		assertMatch("{}*()"
		assertMatch("[a{}()b][a{}()b]?[a{}()b][a{}()b]"
		assertMatch("x*{x}3"
		assertMatch("a*{x}3"
	}

