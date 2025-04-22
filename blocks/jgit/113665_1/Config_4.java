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
