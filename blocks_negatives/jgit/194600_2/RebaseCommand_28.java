	private static String stripCommentLines(String commitMessage) {
		StringBuilder result = new StringBuilder();
		}
			int bufferSize = result.length();
			if (bufferSize > 0 && result.charAt(bufferSize - 1) == '\n') {
				result.deleteCharAt(bufferSize - 1);
			}
		}
		return result.toString();
	}

