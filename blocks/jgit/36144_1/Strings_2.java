	static boolean isComplexWildcard(String pattern) {
		return pattern.indexOf('?') != -1 || pattern.indexOf('[') != -1
				|| pattern.indexOf(']') != -1
				|| pattern.indexOf('\\') != -1;
	}

	static boolean hasLeadingAsteriskOnly(String pattern) {
		return pattern.lastIndexOf('*') == 0 && !isComplexWildcard(pattern);
	}

	static boolean hasTrailingAsteriskOnly(String pattern) {
		return pattern.indexOf('*') == pattern.length() - 1
				&& !isComplexWildcard(pattern);
	}

