			boolean anyMatches = anyMatches(res, patterns);
			boolean patternMatches = patternMatcher.matches(res);
			if (patternMatches) {
				assertTrue("Pattern '" + patternText + "' matches '" + res + "' but other patterns don't.", anyMatches);
			} else {
				assertFalse("Pattern '" + patternText + "' doesn't match '" + res + "' but other patterns do.",
						anyMatches);
			}
