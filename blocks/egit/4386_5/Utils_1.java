	public static String normalizeLineEndings(String s) {
		if (s == null)
			return null;
		if (s.length() == 0)
			return EMPTY_STRING;
		StringBuilder result = new StringBuilder();
		int length = s.length();
		int i = 0;
		while (i < length) {
			if (s.charAt(i) == CR_CHAR) {
				if (i + 1 < length) {
					if (s.charAt(i + 1) == LF_CHAR) {
						result.append(LF_CHAR);
						i += 1;
					} else {
						result.append(LF_CHAR);
					}
				} else {
					result.append(LF_CHAR);
				}
			} else
				result.append(s.charAt(i));
			i++;
		}
		return result.toString();
	}
