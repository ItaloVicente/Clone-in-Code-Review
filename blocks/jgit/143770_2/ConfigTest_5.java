	@Test
	public void testCrLf() throws ConfigInvalidException {
		assertEquals("true"
	}

	@Test
	public void testLfContinuation() throws ConfigInvalidException {
		assertEquals("true"
	}

	@Test
	public void testCrCharContinuation() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage("Bad escape: \\u000d");
		parseEscapedValue("tr\\\rue");
	}

	@Test
	public void testCrEOFContinuation() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage("Bad escape: \\u000d");
		parseEscapedValue("tr\\\r");
	}

	@Test
	public void testCrLfContinuation() throws ConfigInvalidException {
		assertEquals("true"
	}

	@Test
	public void testWhitespaceContinuation() throws ConfigInvalidException {
		assertEquals("tr   ue"
		assertEquals("tr   ue"
	}

