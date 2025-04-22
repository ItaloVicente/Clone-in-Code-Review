	private String parseHeading(String buffer, int start, int end) {
		while (Character.isWhitespace(buffer.charAt(end - 1)) && end > start) {
			end--;
		}
		return buffer.substring(start, end);
	}
