
	@Test
	public void testEscapeSpacesOnly() throws ConfigInvalidException {
		assertEquals(""
		assertEquals("\" \""
		assertEquals("\"  \""

		assertParseRoundTrip(" ");
		assertParseRoundTrip("  ");
	}

	@Test
	public void testEscapeLeadingSpace() throws ConfigInvalidException {
		assertEquals("x"
		assertEquals("\" x\""
		assertEquals("\"  x\""

		assertParseRoundTrip("x");
		assertParseRoundTrip(" x");
		assertParseRoundTrip("  x");
	}

	@Test
	public void testEscapeTrailingSpace() throws ConfigInvalidException {
		assertEquals("x"
		assertEquals("\"x  \""
		assertEquals("x\" \""

		assertParseRoundTrip("x");
		assertParseRoundTrip("x ");
		assertParseRoundTrip("x  ");
	}

	@Test
	public void testEscapeLeadingAndTrailingSpace()
			throws ConfigInvalidException {
		assertEquals("\" x \""
		assertEquals("\"  x \""
		assertEquals("\" x  \""
		assertEquals("\"  x  \""

		assertParseRoundTrip(" x ");
		assertParseRoundTrip(" x  ");
		assertParseRoundTrip("  x ");
		assertParseRoundTrip("  x  ");
	}

	private static void assertParseRoundTrip(String value)
			throws ConfigInvalidException {
		Config c = parse("[foo]\nbar = " + Config.escapeValue(value));
		assertEquals(value
	}
