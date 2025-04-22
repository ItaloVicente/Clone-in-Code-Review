	public DescribeCommand setMatch(String match) throws InvalidPatternException {
		matcher = PathMatcher.createPathMatcher(match
		return this;
	}

	private boolean tagMatches(Ref tag) {
		return (tag != null &&
				(matcher == null || matcher.matches(tag.getName()
	}

