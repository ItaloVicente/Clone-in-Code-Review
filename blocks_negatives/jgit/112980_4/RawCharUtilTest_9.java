	public void testTrimLeadingWhitespace() throws UnsupportedEncodingException {
		assertEquals(0, trimLeadingWhitespace("".getBytes("US-ASCII"), 0, 0));
		assertEquals(1, trimLeadingWhitespace(" ".getBytes("US-ASCII"), 0, 1));
		assertEquals(0, trimLeadingWhitespace("a ".getBytes("US-ASCII"), 0, 2));
		assertEquals(1, trimLeadingWhitespace(" a ".getBytes("US-ASCII"), 0, 3));
		assertEquals(2, trimLeadingWhitespace("  a".getBytes("US-ASCII"), 0, 3));
		assertEquals(2, trimLeadingWhitespace("  test   ".getBytes("US-ASCII"),
