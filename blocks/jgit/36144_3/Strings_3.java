		return pattern.indexOf('*') != -1 || isComplexWildcard(pattern);
	}

	private static boolean isComplexWildcard(String pattern) {
		int idx1 = pattern.indexOf('[');
		if (idx1 != -1) {
			int idx2 = pattern.indexOf(']');
			if (idx2 > idx1)
				return true;
		}
		if (pattern.indexOf('?') != -1 || pattern.indexOf('\\') != -1)
			return true;
		return false;
	}

	static PatternState checkWildCards(String pattern) {
		if (isComplexWildcard(pattern))
			return PatternState.COMPLEX;
		int startIdx = pattern.indexOf('*');
		if (startIdx < 0)
			return PatternState.NONE;

		if (startIdx == pattern.length() - 1)
			return PatternState.TRAILING_ASTERISK_ONLY;
		if (pattern.lastIndexOf('*') == 0)
			return PatternState.LEADING_ASTERISK_ONLY;

		return PatternState.COMPLEX;
	}

	static enum PatternState {
		LEADING_ASTERISK_ONLY
