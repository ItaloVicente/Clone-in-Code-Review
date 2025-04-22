	private static final String transformWildcardToRegex(String pattern) {
		char[] chars = pattern.toCharArray();
		int len = chars.length;
		StringBuilder sb = new StringBuilder();
		boolean quoting = false;
		boolean prevStar = false;
		boolean prevChar = false;
		for (int i = 0; i < len; i++) {
			char c = chars[i];
			boolean isWild = isWildcard(c);
			if (isWild) {
				if (quoting) {
					sb.append(QUOTE_END);
					quoting = false;
				}
				if (c == '*') {
					if (prevStar) {
						continue;
					}
					sb.append(DOT_STAR_LAZY);
				} else {
					sb.append(DOT);
				}
				if (i < len - 1 && !isWildcard(chars[i + 1])) {
					sb.append(QUOTE_START);
					quoting = true;
				}
