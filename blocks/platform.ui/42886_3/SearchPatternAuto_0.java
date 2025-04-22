		assertEquals(patternMatcher.getMatchRule(), searchPattern);
		for (String res : resources) {
			assertEquals(patternMatcher.matches(res), anyMatches(res, patterns));
		}
	}

	private static boolean anyMatches(String res, Pattern... patterns) {
		boolean result = false;
		for (Pattern pattern : patterns) {
			result |= pattern.matcher(res).matches();
