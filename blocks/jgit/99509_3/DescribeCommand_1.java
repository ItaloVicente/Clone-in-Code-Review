	public DescribeCommand setMatch(String... patterns) throws InvalidPatternException {
		for (String p : patterns) {
			matchers.add(PathMatcher.createPathMatcher(p
		}
		return this;
	}

	private boolean tagMatches(Ref tag) {
		if (tag == null) {
			return false;
		} else if (matchers.size() == 0) {
			return true;
		} else {
			return matchers.stream()
					.anyMatch(m -> m.matches(tag.getName()
		}
	}

