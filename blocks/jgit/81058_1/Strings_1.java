	public static String stripTrailingWhitespace(String pattern) {
		for (int i = pattern.length() - 1; i >= 0; i--) {
			char charAt = pattern.charAt(i);
			if (!Character.isWhitespace(charAt)) {
				if (i == pattern.length() - 1) {
					return pattern;
				}
				return pattern.substring(0
			}
		}
		return pattern;
	}

	public static boolean isDirectoryPattern(String pattern) {
		for (int i = pattern.length() - 1; i >= 0; i--) {
			char charAt = pattern.charAt(i);
			if (!Character.isWhitespace(charAt)) {
				return charAt == FastIgnoreRule.PATH_SEPARATOR;
			}
		}
		return false;
	}

