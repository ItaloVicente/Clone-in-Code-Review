	private static boolean isHostMatch(final String pattern, final String name) {
		final FileNameMatcher fn;
		try {
			fn = new FileNameMatcher(pattern, null);
		} catch (InvalidPatternException e) {
			return false;
