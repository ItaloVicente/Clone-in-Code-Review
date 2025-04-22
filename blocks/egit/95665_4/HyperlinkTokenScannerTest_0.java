	@Test
	public void tokenizeWithFailingDetector() {
		String testString = "Link: http://foo bar";
		String expected = "DDDDDDHHHHHHHHHHDDDD";
		assertTokens(testString, 0, testString.length(), detectorsWithFailure,
				expected);
		expected = "DDDDDDDDDDDDDDDDDDDD";
		assertTokens(testString, 0, testString.length(),
				new IHyperlinkDetector[] { new CrashingHyperlinkDetector() },
				expected);
	}

	@Test
	public void tokenizeWithoutDetectors() {
		String testString = "Link: http://foo bar";
		String expected = "DDDDDDDDDDDDDDDDDDDD";
		assertTokens(testString, 0, testString.length(),
				new IHyperlinkDetector[] {}, expected);
	}

