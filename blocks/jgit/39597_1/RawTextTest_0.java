	@Test
	public void testLineDelimiter2() throws Exception {
		RawText rt = new RawText(Constants.encodeASCII("\nfoo"));
		assertEquals("\n"
		assertTrue(rt.isMissingNewlineAtEnd());
	}

