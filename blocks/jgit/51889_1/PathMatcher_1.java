	private static String trim(String pattern) {
		while (pattern.length() > 0
				&& pattern.charAt(pattern.length() - 1) == ' ') {
			if (pattern.length() > 1
					&& pattern.charAt(pattern.length() - 2) == '\\') {
				pattern = pattern.substring(0
				return pattern;
			}
			pattern = pattern.substring(0
		}
		return pattern;
	}

