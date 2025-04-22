			r.append((char) c);
		}
		return r.toString();
	}

	private static String readValue(final StringReader in)
			throws ConfigInvalidException {
		StringBuilder value = new StringBuilder();
		StringBuilder trailingSpaces = null;
		boolean quote = false;
		boolean inLeadingSpace = true;

		for (;;) {
			int c = in.read();
			if (c < 0) {
				break;
			}
			if ('\n' == c) {
				if (quote) {
					throw new ConfigInvalidException(
							JGitText.get().newlineInQuotesNotAllowed);
				}
				in.reset();
