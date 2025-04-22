		this();
		try {
			parse(pattern);
		} catch (InvalidPatternException e) {
			LOG.error(MessageFormat.format(JGitText.get().badIgnorePattern
					e.getPattern())
		}
	}

	FastIgnoreRule() {
		matcher = IMatcher.NO_MATCH;
	}

	void parse(String pattern) throws InvalidPatternException {
		if (pattern == null) {
