
	public static int getFooterOffset(String commitMessage) {
		if (commitMessage == null) {
			return -1;
		}
		Matcher matcher = FOOTER_PATTERN.matcher(commitMessage);
		if (matcher.find()) {
			int start = matcher.start();
			int i = start - 1;
			while (i >= 0) {
				char ch = commitMessage.charAt(i--);
				if (ch == '\n') {
					return start + 1;
				} else if (!Character.isWhitespace(ch)) {
					return -1;
				}
			}
			return start + 1;
		}
		return -1;
	}
