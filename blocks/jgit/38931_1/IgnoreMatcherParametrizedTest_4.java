		boolean match;
		if (useOldRule.booleanValue()) {
			IgnoreRule r = new IgnoreRule(pattern);
			match = r.isMatch(target
		} else {
			FastIgnoreRule r = new FastIgnoreRule(pattern);
			match = r.isMatch(target
		}

		if (isDirectory) {
			boolean noTrailingSlash = matchAsDir(pattern
					target.substring(0
			if (match != noTrailingSlash) {
				String message = "Difference in result for directory pattern: "
						+ pattern + " with: " + target
						+ " if target is given without trailing slash";
				Assert.assertEquals(message
			}
		}
		return match;
	}

	private boolean matchAsDir(String pattern
		assertFalse(target.endsWith("/"));
