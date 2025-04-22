		assertTokens(text, offset, length, detectors, expected);
	}

	@SuppressWarnings("boxing")
	private void assertTokens(String text, int offset, int length,
			IHyperlinkDetector[] hyperlinkDetectors, String expected) {
