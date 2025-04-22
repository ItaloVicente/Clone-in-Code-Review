	private static final String escapeWhitespace(String value) {
		final StringBuilder escaped = new StringBuilder(value);
		final int length = escaped.length();
		for (int i = 0; i < length; i++) {
			char c = escaped.charAt(i);
			if (c != ' ' && Character.isWhitespace(c))
				escaped.setCharAt(i, ' ');
		}
		return escaped.toString();
	}

