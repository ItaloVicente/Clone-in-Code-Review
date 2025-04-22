
		if (windows && (raw[end - 1] == ' ' || raw[end - 1] == '.'))
			throw new CorruptObjectException("invalid name ends with '"
					+ ((char) raw[end - 1]) + "'");
	}

	private static boolean isInvalidOnWindows(byte c) {
		switch (c) {
		case '"':
		case '*':
		case ':':
		case '<':
		case '>':
		case '?':
		case '\\':
		case '|':
			return true;
		}
		return 1 <= c && c <= 31;
