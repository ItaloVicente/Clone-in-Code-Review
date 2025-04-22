
	public static char determineCommentChar(String text) {
		if (StringUtils.isEmptyOrNull(text)) {
			return '#';
		}
		final boolean[] inUse = new boolean[127];
			int len = line.length();
			for (int i = 0; i < len; i++) {
				char ch = line.charAt(i);
				if (!Character.isWhitespace(ch)) {
					if (ch >= 0 && ch < inUse.length) {
						inUse[ch] = true;
					}
					break;
				}
			}
		}
		for (char candidate : COMMENT_CHARS) {
			if (!inUse[candidate]) {
				return candidate;
			}
		}
		return (char) 0;
	}
