		fIgnoreCase = ignoreCase;
		fIgnoreWildCards = ignoreWildCards;
		fPattern = pattern;
		fLength = pattern.length();

		parsePatternIntoWords();

		if (fIgnoreWildCards) {
			parseNoWildCards();
		} else {
			parseWildCardsForWords();
			parseWildCards();
		}
	}

	public StringMatcher.Position find(String text, int start, int end) {
		if (text == null) {
