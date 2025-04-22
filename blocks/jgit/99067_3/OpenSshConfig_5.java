	private static boolean patternMatchesHost(final String pattern
			final String name) {
		if (pattern.indexOf('*') >= 0 || pattern.indexOf('?') >= 0) {
			final FileNameMatcher fn;
			try {
				fn = new FileNameMatcher(pattern
			} catch (InvalidPatternException e) {
				return false;
			}
			fn.append(name);
			return fn.isMatch();
		} else {
			return pattern.equals(name);
