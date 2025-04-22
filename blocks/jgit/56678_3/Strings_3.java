	static String deleteBackslash(String s) {
		if (s.indexOf('\\') < 0) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == '\\') {
				if (i + 1 == s.length()) {
					continue;
				}
				char next = s.charAt(i + 1);
				if (next == '\\') {
					sb.append(ch);
					i++;
					continue;
				}
				if (!escapedByBackslash(next)) {
					continue;
				}
			}
			sb.append(ch);
		}
		return sb.toString();
	}

