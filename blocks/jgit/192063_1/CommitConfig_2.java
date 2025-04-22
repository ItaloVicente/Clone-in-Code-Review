	public char getCommentChar() {
		return commentCharacter;
	}

	public char getCommentChar(String text) {
		if (isAutoCommentChar()) {
			char toUse = determineCommentChar(text);
			if (toUse > 0) {
				return toUse;
			}
			return '#';
		}
		return getCommentChar();
	}

	public boolean isAutoCommentChar() {
		return autoCommentChar;
	}

