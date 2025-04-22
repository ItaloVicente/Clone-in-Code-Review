	Pattern getFilterPattern() {
		return filterPattern;
	}

	private Pattern wildcardToRegex(String filter) {
		String trimmed = filter.trim();
		if (trimmed.isEmpty()) {
			return null;
		}
		String regex = (trimmed.contains("*") ? "^" : "") + "\\Q"//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ trimmed.replaceAll("\\*", //$NON-NLS-1$
						Matcher.quoteReplacement("\\E.*?\\Q")) //$NON-NLS-1$
				+ "\\E";//$NON-NLS-1$
		regex = regex.replaceAll(Pattern.quote("\\Q\\E"), ""); //$NON-NLS-1$ //$NON-NLS-2$
		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
