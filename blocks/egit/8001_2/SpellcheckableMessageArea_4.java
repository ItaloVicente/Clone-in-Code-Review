	public static int[] calculateWrapOffsets(final String line, final int maxLineLength) {
		if (line.length() == 0)
			return null;

		IntList wrapOffsets = new IntList();
		int wordStart = 0;
		int lineStart = 0;
		boolean lastWasSpace = true;
		boolean onlySpaces = true;
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (ch == ' ') {
				lastWasSpace = true;
			} else if (ch == '\n') {
				lineStart = i + 1;
				wordStart = i + 1;
				lastWasSpace = true;
				onlySpaces = true;
			} else { // a word character
				if (lastWasSpace) {
					lastWasSpace = false;
					if (!onlySpaces) { // don't break line with <spaces><veryLongWord>
						wordStart = i;
