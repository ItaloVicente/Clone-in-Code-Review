
	public static String wrap(final String text, final int maxLineLength) {
		String[] chunks = text.split("\n"); //$NON-NLS-1$
		StringBuilder wrappedText = new StringBuilder(text.length());

		for (int chunkIndex = 0; chunkIndex < chunks.length; chunkIndex++) {
			String chunk = chunks[chunkIndex];

			String[] words = chunk.split(" "); //$NON-NLS-1$
			int lineLength = 0;

			for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
				String word = words[wordIndex];

				int wordLength = word.length();
				int newLineLength = lineLength + wordLength + 1 /* the space */;
				if (newLineLength > maxLineLength) {
					if (lineLength != 0) {
						wrappedText.append('\n');
					}
					lineLength = 0;
				}
				if (lineLength != 0) {
					wrappedText.append(' ');
					lineLength += 1;
				}
				wrappedText.append(word);
				lineLength += wordLength;
			}

			if (chunkIndex != chunks.length - 1) {
				wrappedText.append('\n');
			}
		}

		return wrappedText.toString();
	}

