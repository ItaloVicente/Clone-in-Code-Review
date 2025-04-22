				throw new ConfigInvalidException(
						JGitText.get().newlineInQuotesNotAllowed);
			}
			if ('\\' == c) {
				c = in.read();
				switch (c) {
				case -1:
					throw new ConfigInvalidException(JGitText.get().endOfFileInEscape);

				case '\\':
				case '"':
					r.append((char) c);
					continue;

				default:
					throw new ConfigInvalidException(MessageFormat.format(
							JGitText.get().badEscape
							Character.valueOf(((char) c))));
				}
			}
			if ('"' == c) {
				break;
			}

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
