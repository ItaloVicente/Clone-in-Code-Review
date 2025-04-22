		return new Position(matchStart, curPos);
	}

	public boolean match(String text) {
		return match(text, 0, text.length());
	}

	public boolean match(String text, int start, int end) {
		if (null == text) {
