		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_CAMELCASE_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			if (patternMatcher.matches(res) != pattern.matcher(res).matches()) {
				assertEquals(patternMatcher.matches(res), pattern2.matcher(res).matches());
			}
		}
