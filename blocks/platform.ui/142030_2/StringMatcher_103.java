		fIgnoreCase = ignoreCase;
		fIgnoreWildCards = ignoreWildCards;
		fPattern = pattern;
		fLength = pattern.length();

		if (fIgnoreWildCards) {
			parseNoWildCards();
		} else {
			parseWildCards();
		}
	}

	public StringMatcher.Position find(String text, int start, int end) {
		if (text == null) {
