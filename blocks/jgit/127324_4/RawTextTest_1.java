	@Test
	public void testCrLfTextYes() {
		assertTrue(RawText
				.isCrLfText(Constants.encodeASCII("line 1\r\nline 2\r\n")));
	}

	@Test
	public void testCrLfTextNo() {
		assertFalse(
				RawText.isCrLfText(Constants.encodeASCII("line 1\nline 2\n")));
	}

	@Test
	public void testCrLfTextBinary() {
		assertFalse(RawText
				.isCrLfText(Constants.encodeASCII("line 1\r\nline\0 2\r\n")));
	}

	@Test
	public void testCrLfTextMixed() {
		assertTrue(RawText
				.isCrLfText(Constants.encodeASCII("line 1\nline 2\r\n")));
	}

	@Test
	public void testCrLfTextCutShort() {
		assertFalse(
				RawText.isCrLfText(Constants.encodeASCII("line 1\nline 2\r")));
	}

