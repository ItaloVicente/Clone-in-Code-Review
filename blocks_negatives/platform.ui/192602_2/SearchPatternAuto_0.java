	/**
	 * Tests exact match functionality. If we camelCase rule is enable, Pattern should starts with lowerCase character.
	 * Result for "abcdefgh&lt;" pattern should be similar to regexp pattern "abcdefgh" with case insensitive.
	 */
	@Test
	public void testExactMatch2() {
		Pattern pattern = Pattern.compile("abcdefgh", Pattern.CASE_INSENSITIVE);
		assertMatches("abcdefgh<", SearchPattern.RULE_EXACT_MATCH, pattern);
	}
