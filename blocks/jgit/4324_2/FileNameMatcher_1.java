	private static int findGroupStart(String pattern
			Character invalidWildgetCharacter) {
		if (invalidWildgetCharacter != null && invalidWildgetCharacter == '\\')
			return pattern.indexOf('['
		return findNotEscapedCharacterPosition(pattern
	}

	private static int findNotEscapedCharacterPosition(String pattern
			int currentIndex
		for (int i = currentIndex; i < pattern.length(); i++) {
			final char c = pattern.charAt(i);
			if (c != characterToFind)
				continue;

			final boolean isEscaped = i > 0 && pattern.charAt(i - 1) == '\\';
			if (isEscaped)
				continue;

			return i;
		}
		return -1;
	}

