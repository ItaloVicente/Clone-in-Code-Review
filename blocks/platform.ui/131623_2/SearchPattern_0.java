	public List<Position> getMatches(String text) {
		assert(text!=null);
		List<Position> matches = new ArrayList<>();
		int start = 0;
		int end = text.length();
		while (stringMatcher != null && start < end && matches(text.substring(start, end))) {
			Position pos = stringMatcher.find(text, start, end);
			matches.add(pos);
			start = pos.end;
		}
		return matches;
	}

