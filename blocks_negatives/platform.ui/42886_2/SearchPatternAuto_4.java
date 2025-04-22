		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_PATTERN_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
