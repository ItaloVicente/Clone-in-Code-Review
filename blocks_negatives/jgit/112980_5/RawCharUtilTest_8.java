	public void testTrimTrailingWhitespace()
			throws UnsupportedEncodingException {
		assertEquals(0, trimTrailingWhitespace("".getBytes("US-ASCII"), 0, 0));
		assertEquals(0, trimTrailingWhitespace(" ".getBytes("US-ASCII"), 0, 1));
		assertEquals(1, trimTrailingWhitespace("a ".getBytes("US-ASCII"), 0, 2));
		assertEquals(2,
				trimTrailingWhitespace(" a ".getBytes("US-ASCII"), 0, 3));
		assertEquals(3,
				trimTrailingWhitespace("  a".getBytes("US-ASCII"), 0, 3));
		assertEquals(6, trimTrailingWhitespace(
				"  test   ".getBytes("US-ASCII"), 2, 9));
