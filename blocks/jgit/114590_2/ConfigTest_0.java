	public void testNoEscapeSpecialCharacters() throws ConfigInvalidException {
		assertValueRoundTrip("x\\y"
		assertValueRoundTrip("x\"y"
		assertValueRoundTrip("x\ny"
		assertValueRoundTrip("x\ty"
		assertValueRoundTrip("x\by"
	}

	@Test
	public void testParseLiteralBackspace() throws ConfigInvalidException {
		assertEquals("x\by"
	}

	@Test
	public void testEscapeCommentCharacters() throws ConfigInvalidException {
