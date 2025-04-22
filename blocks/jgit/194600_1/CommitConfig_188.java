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

	@NonNull
	public CleanupMode getCleanupMode() {
		return cleanupMode;
	}

	@NonNull
	public CleanupMode resolve(@NonNull CleanupMode mode
			boolean defaultStrip) {
		if (CleanupMode.DEFAULT == mode) {
			CleanupMode defaultMode = getCleanupMode();
			if (CleanupMode.DEFAULT == defaultMode) {
				return defaultStrip ? CleanupMode.STRIP
						: CleanupMode.WHITESPACE;
			}
			return defaultMode;
		}
		return mode;
	}

