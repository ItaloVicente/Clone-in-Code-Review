	private static Matcher matcher(String filter) {
		String pattern = filter;
		if (StringUtils.isEmptyOrNull(pattern)) {
			return s -> true;
		}
		boolean frontAnchored = pattern.charAt(0) == '^';
		if (frontAnchored) {
			pattern = pattern.substring(1);
		}
		boolean endAnchored = !pattern.isEmpty()
				&& pattern.charAt(pattern.length() - 1) == '$';
		if (endAnchored) {
			pattern = pattern.substring(0, pattern.length() - 1);
		}
		if (pattern.isEmpty()) {
			return s -> true;
		}
		if (!frontAnchored) {
			pattern = '*' + pattern;
		}
		pattern = fixTrailingBackslash(pattern);
		pattern += endAnchored ? ' ' : '*';
		SearchPattern matcher = new SearchPattern(
				SearchPattern.RULE_PATTERN_MATCH);
		matcher.setPattern(pattern);
		return matcher::matches;
	}

	private static String fixTrailingBackslash(String text) {
		int l = text.length();
		int i = l;
		for (; i > 0; i--) {
			if (text.charAt(i - 1) != '\\') {
				break;
			}
		}
		if ((l - i) % 2 == 1) {
			return text + '\\';
		}
		return text;
	}

