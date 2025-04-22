
	public static char commentCharFromMergeMessage(String message) {
		Pattern CONFLICT_LINE = Pattern.compile("(?:^|\\R)(.) Conflicts:\\R"); //$NON-NLS-1$
		Matcher m = CONFLICT_LINE.matcher(message);
		if (m.find()) {
			return m.group(1).charAt(0);
		}
		return '\0';
	}
