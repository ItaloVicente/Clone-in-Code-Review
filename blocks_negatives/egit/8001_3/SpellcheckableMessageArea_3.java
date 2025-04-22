	public static List<WrapEdit> calculateWrapEdits(final String text, final int maxLineLength, final String lineDelimiter) {
		List<WrapEdit> wrapEdits = new LinkedList<WrapEdit>();

		final int lineDelimiterLength = lineDelimiter.length();
		final int spaceLength = 1;

		int offset = 0;
		boolean lastChunkWasWrapped = false;
		int lastChunkWrappedOffset = 0;

		String[] chunks = text.split(lineDelimiter, -1);
		for (int chunkIndex = 0; chunkIndex < chunks.length; chunkIndex++) {
			String chunk = chunks[chunkIndex];

			String[] words = chunk.split(" ", -1); //$NON-NLS-1$
			int lineLength = 0;

			for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
				String word = words[wordIndex];
				int wordLength = word.length();
				boolean adjustForSpace = wordIndex != 0;

				if (wordIndex == 0 && lastChunkWasWrapped) {
					if (wordLength != 0 && !STARTS_WITH_SYMBOL.matcher(word).matches()) {
						wrapEdits.add(new WrapEdit(offset - lineDelimiterLength, lineDelimiterLength, " ")); //$NON-NLS-1$
						/* adjust for join edit above */
						offset -= lineDelimiterLength;
						adjustForSpace = true;
						lineLength = offset - lastChunkWrappedOffset;
