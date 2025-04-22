		IMatcher m;
		try {
			m = PathMatcher.createPathMatcher(pattern,
					Character.valueOf(PATH_SEPARATOR), dirOnly);
		} catch (InvalidPatternException e) {
			m = NO_MATCH;
			LOG.error(MessageFormat.format(
					JGitText.get().badIgnorePattern,
					e.getPattern()), e);
		}
		this.matcher = m;
