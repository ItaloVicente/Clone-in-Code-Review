	@Test
	public void testNoEscapeInternalSpaces() throws ConfigInvalidException {
		assertValueRoundTrip("x y");
		assertValueRoundTrip("x  y");
		assertValueRoundTrip("x  y");
		assertValueRoundTrip("x  y   z");

		assertValueRoundTrip("x \u2002 y");
