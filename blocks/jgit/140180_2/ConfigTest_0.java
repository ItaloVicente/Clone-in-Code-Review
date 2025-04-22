	@Test
	public void testInvalidGroupHeader() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(JGitText.get().badGroupHeader);
		parse("[foo \"bar\" ]\nfoo=bar\n");
	}

