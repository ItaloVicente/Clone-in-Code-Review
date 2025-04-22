	private static int countLines(String text) {
		int newLines = 1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				newLines++;
			}
		}
		return newLines;
	}
