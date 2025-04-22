		if (useOldRule.booleanValue()) {
			final IgnoreRule matcher = new IgnoreRule(pattern);
			assertEquals(matchExpected, matcher.isMatch(input, assumeDir));
		} else {
			FastIgnoreRule matcher = new FastIgnoreRule(pattern);
			assertEquals(matchExpected, matcher.isMatch(input, assumeDir));
		}
