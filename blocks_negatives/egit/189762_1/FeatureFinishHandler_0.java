
	private static String stripCommentLines(String commitMessage) {
		StringBuilder result = new StringBuilder();
		}
			result.deleteCharAt(result.length() - 1);
		return result.toString();
	}
