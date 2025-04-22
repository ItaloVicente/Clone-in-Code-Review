	private String escapeSpecialCharacters(String filterPattern) {
		boolean startsWithSpecialChar = filterPattern.startsWith(ASTERISK) || filterPattern.startsWith(QMARK);
		boolean endsWithSpecialChar = filterPattern.endsWith(ASTERISK) || filterPattern.endsWith(QMARK);
		filterPattern = filterPattern.replace(ASTERISK, "\\E).*(\\Q"); //$NON-NLS-1$
		filterPattern = filterPattern.replace(QMARK, "\\E).(\\Q"); //$NON-NLS-1$

		if (!startsWithSpecialChar) {
		}
		if (!endsWithSpecialChar) {
		}


		return filterPattern.substring(start, end);
