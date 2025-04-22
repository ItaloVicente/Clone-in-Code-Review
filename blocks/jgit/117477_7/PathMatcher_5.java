
		final boolean matches = matcher.matches(path
		if (!matches || !pathMatch || matcherIdx < matchers.size() - 1
				|| !(matcher instanceof AbstractMatcher)) {
			return matches;
		}

		return assumeDirectory || !((AbstractMatcher) matcher).dirOnly;
	}

	private static boolean isWild(IMatcher matcher) {
		return matcher == WILD_NO_DIRECTORY || matcher == WILD_ONLY_DIRECTORY;
