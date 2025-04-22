	}

	/**
	 * Tests camelCase match functionality.
	 * Every string starts with upperCase characters should be recognize as camelCase pattern match rule.
	 * Result for "AbCdE&lt;" SearchPattern should be similar to regexp pattern "Ab[^A-Z]*Cd[^A-Z]*E[^A-Z]*"
	 */
	@Test
	public void testCamelCaseMatch3() {
		Pattern pattern = Pattern.compile("Ab[^A-Z]*Cd[^A-Z]*E[^A-Z]*");
		assertMatches("AbCdE<", SearchPattern.RULE_CAMELCASE_MATCH, pattern);
