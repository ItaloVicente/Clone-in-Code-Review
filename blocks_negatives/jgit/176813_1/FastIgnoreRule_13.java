		IMatcher m;
		try {
			m = PathMatcher.createPathMatcher(pattern,
					Character.valueOf(PATH_SEPARATOR), dirOnly);
		} catch (InvalidPatternException e) {
			m = NO_MATCH;
			LOG.error(e.getMessage(), e);
		}
		this.matcher = m;
