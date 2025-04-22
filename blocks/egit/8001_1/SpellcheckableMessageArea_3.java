	public static int[] calculateWrapOffsets(final String line, final int maxLineLength) {
		if (line.length() == 0)
			return null;

		IntList wrapOffsets = new IntList();
		int wordStart = 0;
		int lineStart = 0;
		boolean wasSpace = true;
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (ch == ' ') {
				wasSpace = true;
			} else if (ch == '\r' || ch == '\n') {
				lineStart = i + 1;
				wordStart = i + 1;
				wasSpace = false;
			} else {
				if (wasSpace) {
					wasSpace = false;
					wordStart = i;
