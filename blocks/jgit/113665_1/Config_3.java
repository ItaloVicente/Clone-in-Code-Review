		StringBuilder r = new StringBuilder(x.length() + 2).append('"');
		for (int k = 0; k < x.length(); k++) {
			char c = x.charAt(k);

			switch (c) {
			case '\0':
				throw new IllegalArgumentException(
						JGitText.get().configSubsectionContainsNullByte);

			case '\n':
				throw new IllegalArgumentException(
						JGitText.get().configSubsectionContainsNewline);

			case '\\':
			case '"':
				r.append('\\').append(c);
				break;

			default:
				r.append(c);
				break;
			}
