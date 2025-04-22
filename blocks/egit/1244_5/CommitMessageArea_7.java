	public static List<WrapEdit> calculateWrapEdits(final String text, final int maxLineLength, final String lineDelimiter) {
		List<WrapEdit> wrapEdits = new LinkedList<WrapEdit>();

		int offset = 0;
		int lineDelimiterLength = lineDelimiter.length();

		String[] chunks = text.split(lineDelimiter, -1);
		for (int chunkIndex = 0; chunkIndex < chunks.length; chunkIndex++) {
			String chunk = chunks[chunkIndex];

			String[] words = chunk.split(" ", -1); //$NON-NLS-1$
			int lineLength = 0;

			for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
				String word = words[wordIndex];

				int wordLength = word.length();
				int newLineLength = lineLength + wordLength + 1 /* the space */;
				if (newLineLength > maxLineLength) {
					if (lineLength != 0) {
						wrapEdits.add(new WrapEdit(offset, 1));
						offset += lineDelimiterLength;
					}
					lineLength = 0;
				} else if (wordIndex != 0) {
					lineLength += 1;
					offset += 1;
				}
				offset += wordLength;
				lineLength += wordLength;
			}

			if (chunkIndex != chunks.length - 1) {
				offset += lineDelimiterLength;
			}
		}

		return wrapEdits;
	}

	public static class WrapEdit {
		private int start;
		private int length;

		public WrapEdit(int start, int length) {
			this.start = start;
			this.length = length;
		}

		public int getStart() {
			return start;
		}

		public int getLength() {
			return length;
		}
	}
