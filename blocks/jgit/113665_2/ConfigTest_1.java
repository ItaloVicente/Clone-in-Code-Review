		assertValueRoundTrip(" x "
		assertValueRoundTrip("  x "
		assertValueRoundTrip(" x  "
		assertValueRoundTrip("  x  "
	}

	@Test
	public void testNoEscapeInternalSpaces() throws ConfigInvalidException {
		assertValueRoundTrip("x y");
		assertValueRoundTrip("x  y");
		assertValueRoundTrip("x  y");
		assertValueRoundTrip("x  y   z");
		assertValueRoundTrip("x " + WS + " y");
	}

	@Test
	public void testEscapeSpecialCharacters() throws ConfigInvalidException {
		assertValueRoundTrip("x#y"
		assertValueRoundTrip("x;y"
		assertValueRoundTrip("x\\y"
		assertValueRoundTrip("x\"y"
		assertValueRoundTrip("x\ny"
		assertValueRoundTrip("x\ty"
		assertValueRoundTrip("x\by"
	}

	@Test
	public void testEscapeValueInvalidCharacters() {
		assertIllegalArgumentException(() -> Config.escapeSubsection("x\0y"));
	}

	@Test
	public void testEscapeSubsectionInvalidCharacters() {
		assertIllegalArgumentException(() -> Config.escapeSubsection("x\ny"));
		assertIllegalArgumentException(() -> Config.escapeSubsection("x\0y"));
	}

	@Test
	public void testParseMultipleQuotedRegions() throws ConfigInvalidException {
		assertEquals("b a z; \n"
	}

	@Test
	public void testParseComments() throws ConfigInvalidException {
		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"

		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"
	}

	@Test
	public void testEscapeSubsection() throws ConfigInvalidException {
		assertSubsectionRoundTrip(""
		assertSubsectionRoundTrip("x"
		assertSubsectionRoundTrip(" x"
		assertSubsectionRoundTrip("x "
		assertSubsectionRoundTrip(" x "
		assertSubsectionRoundTrip("x y"
		assertSubsectionRoundTrip("x  y"
		assertSubsectionRoundTrip("x\\y"
		assertSubsectionRoundTrip("x\"y"

		assertSubsectionRoundTrip("x\by"
		assertSubsectionRoundTrip("x\ty"
	}

	@Test
	public void testParseInvalidValues() {
		assertInvalidValue(JGitText.get().newlineInQuotesNotAllowed
		assertInvalidValue(JGitText.get().endOfFileInEscape
		assertInvalidValue(
				MessageFormat.format(JGitText.get().badEscape
	}

	@Test
	public void testParseInvalidSubsections() {
		assertInvalidSubsection(
				JGitText.get().newlineInQuotesNotAllowed
		assertInvalidSubsection(
				MessageFormat.format(JGitText.get().badEscape

		assertInvalidSubsection(
				MessageFormat.format(JGitText.get().badEscape
		assertInvalidSubsection(
				MessageFormat.format(JGitText.get().badEscape
		assertInvalidSubsection(
				MessageFormat.format(JGitText.get().badEscape
	}

	private static void assertValueRoundTrip(String value)
			throws ConfigInvalidException {
		assertValueRoundTrip(value
	}

	private static void assertValueRoundTrip(String value
			throws ConfigInvalidException {
		String escaped = Config.escapeValue(value);
		assertEquals("escape failed;"
		assertEquals("parse failed;"
	}

	private static String parseEscapedValue(String escapedValue)
			throws ConfigInvalidException {
		String text = "[foo]\nbar=" + escapedValue;
		Config c = parse(text);
		return c.getString("foo"
	}

	private static void assertInvalidValue(String expectedMessage
			String escapedValue) {
		try {
			parseEscapedValue(escapedValue);
			fail("expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			assertEquals(expectedMessage
		}
	}
