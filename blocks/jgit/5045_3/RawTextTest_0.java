	@Test
	public void testEOL() throws Exception {
		RawText rt = new RawText(Constants.encodeASCII("foo\n"));
		assertEquals("\n"
		assertFalse(rt.isMissingNewlineAtEnd());
		rt = new RawText(Constants.encodeASCII("foo\r\n"));
		assertEquals("\r\n"
		assertFalse(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("foo\nbar"));
		assertEquals("\n"
		assertTrue(rt.isMissingNewlineAtEnd());
		rt = new RawText(Constants.encodeASCII("foo\r\nbar"));
		assertEquals("\r\n"
		assertTrue(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("foo\nbar\r\n"));
		assertEquals("\n"
		assertFalse(rt.isMissingNewlineAtEnd());
		rt = new RawText(Constants.encodeASCII("foo\r\nbar\n"));
		assertEquals("\r\n"
		assertFalse(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("foo"));
		assertNull(rt.getEOL());
		assertTrue(rt.isMissingNewlineAtEnd());
	}

