
	public static String cleanText(@NonNull String text
			@NonNull CleanupMode mode
		String toProcess = text;
		boolean strip = false;
		switch (mode) {
		case VERBATIM:
			return text;
		case SCISSORS:
			String cut = commentChar + CUT;
			if (text.startsWith(cut)) {
			}
			int cutPos = text.indexOf('\n' + cut);
			if (cutPos >= 0) {
				toProcess = text.substring(0
			}
			break;
		case STRIP:
			strip = true;
			break;
		case WHITESPACE:
			break;
		case DEFAULT:
		default:
		}
		StringBuilder result = new StringBuilder();
		boolean lastWasEmpty = true;
			line = line.stripTrailing();
			if (line.isEmpty()) {
				if (!lastWasEmpty) {
					result.append('\n');
					lastWasEmpty = true;
				}
			} else if (!strip || !isComment(line
				lastWasEmpty = false;
				result.append(line).append('\n');
			}
		}
		int bufferSize = result.length();
		if (lastWasEmpty && bufferSize > 0) {
			bufferSize--;
			result.setLength(bufferSize);
		}
			if (result.charAt(bufferSize - 1) == '\n') {
				result.setLength(bufferSize - 1);
			}
		}
		return result.toString();
	}

	private static boolean isComment(String text
		int len = text.length();
		for (int i = 0; i < len; i++) {
			char ch = text.charAt(i);
			if (!Character.isWhitespace(ch)) {
				return ch == commentChar;
			}
		}
		return false;
	}

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
