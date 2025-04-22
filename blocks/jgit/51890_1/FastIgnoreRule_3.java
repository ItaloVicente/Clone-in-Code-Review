		IMatcher m;
		try {
			m = PathMatcher.createPathMatcher(pattern
					Character.valueOf(PATH_SEPARATOR)
		} catch (InvalidPatternException e) {
			m = NO_MATCH;
		}
		this.matcher = m;
