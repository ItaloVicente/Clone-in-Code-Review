		if (useOldRule.booleanValue()) {
			final IgnoreRule matcher = new IgnoreRule(pattern);
			if (assume.length == 0 || !assume[0].booleanValue())
				assertEquals(matchExpected, matcher.isMatch(input, assumeDir));
			else
				assumeTrue(matchExpected == matcher.isMatch(input, assumeDir));
