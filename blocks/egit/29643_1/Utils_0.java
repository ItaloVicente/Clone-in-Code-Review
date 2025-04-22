
	public static String shortenText(final String text, final int maxLength) {
		if (text.length() > maxLength)
			return text.substring(0, maxLength - 1) + "\u2026"; // ellipsis "…" (in UTF-8) //$NON-NLS-1$
		return text;
	}
