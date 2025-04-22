	Pattern getFilterPattern() {
		return filterPattern;
	}

	private Pattern wildcardToRegex(String filter) {
		String regex = "(?i)\\Q" + filter.trim().replaceAll("\\Q*\\E", //$NON-NLS-1$ //$NON-NLS-2$
				Matcher.quoteReplacement("\\E.*?\\Q")) + "\\E";//$NON-NLS-1$ //$NON-NLS-2$
		regex = regex.replace("\\Q\\E", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return Pattern.compile(regex);
