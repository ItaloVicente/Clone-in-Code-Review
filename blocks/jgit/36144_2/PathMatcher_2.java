
		PatternState state = checkWildCards(segment);
		switch (state) {
		case LEADING_ASTERISK_ONLY:
			return new LeadingAsteriskMatcher(segment
		case TRAILING_ASTERISK_ONLY:
			return new TrailingAsteriskMatcher(segment
		case COMPLEX:
