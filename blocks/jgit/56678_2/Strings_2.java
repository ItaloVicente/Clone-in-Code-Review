	static String deleteBackslash(String s) {
		if (s.indexOf('\\') < 0) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length());
		char prev = 0;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ('\\' == ch) {
				if (i + 1 == s.length()) {
					continue;
				}
				char next = s.charAt(i + 1);
				if (prev != '\\' && !escapedByBackslash(next)) {
					continue;
				}
				prev = ch;
			} else {
				prev = 0;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

