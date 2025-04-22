
	@FunctionalInterface
	private interface CharSupplier {

		char get();
	}

	private static class CommitPartitionTokenScanner
			implements IPartitionTokenScanner {

		private static final String CUT = " ------------------------ >8 ------------------------"; //$NON-NLS-1$

		private static final IToken COMMENT = new Token(COMMENT_CONTENT_TYPE);

		private static final IToken DEFAULT = new Token(null);

		private final Supplier<CleanupMode> cleanupMode;

		private final CharSupplier commentChar;

		private IDocument currentDoc;

		private int currentOffset;

		private int end;

		private int tokenStart;

		public CommitPartitionTokenScanner(CharSupplier commentChar,
				Supplier<CleanupMode> mode) {
			super();
			this.commentChar = commentChar;
			this.cleanupMode = mode;
		}

		@Override
		public void setRange(IDocument document, int offset, int length) {
			currentDoc = document;
			currentOffset = offset;
			end = offset + length;
			tokenStart = -1;
		}

		@Override
		public IToken nextToken() {
			tokenStart = currentOffset;
			if (tokenStart >= end) {
				return Token.EOF;
			}
			CleanupMode mode = cleanupMode.get();
			char commentCharacter = commentChar.get();
			if (CleanupMode.SCISSORS.equals(mode)) {
				int scissors = -1;
				int nOfLines = currentDoc.getNumberOfLines();
				try {
					String cut = commentCharacter + CUT;
					for (int i = 0; i < nOfLines; i++) {
						IRegion info = currentDoc.getLineInformation(i);
						String line = currentDoc.get(info.getOffset(),
								info.getLength());
						if (line.equals(cut)) {
							scissors = info.getOffset();
							break;
						}
					}
				} catch (BadLocationException e) {
				}
				if (scissors < 0) {
					currentOffset = end;
					return DEFAULT;
				} else if (currentOffset < scissors) {
					currentOffset = scissors;
					return DEFAULT;
				}
				currentOffset = end;
				return COMMENT;
			}
			if (!CleanupMode.STRIP.equals(mode)) {
				currentOffset = end;
				return DEFAULT;
			}
			try {
				int nOfLines = currentDoc.getNumberOfLines();
				int lineNumber = currentDoc.getLineOfOffset(currentOffset);
				int initialLine = lineNumber;
				IToken result = DEFAULT;
				while (lineNumber < nOfLines) {
					IRegion info = currentDoc.getLineInformation(lineNumber);
					String line = currentDoc.get(info.getOffset(),
							info.getLength());
					if (isComment(line, commentCharacter)) {
						break;
					}
					lineNumber++;
				}
				if (lineNumber == initialLine) {
					lineNumber++;
					while (lineNumber < nOfLines) {
						IRegion info = currentDoc
								.getLineInformation(lineNumber);
						String line = currentDoc.get(info.getOffset(),
								info.getLength());
						if (!isComment(line, commentCharacter)) {
							break;
						}
						lineNumber++;
					}
					result = COMMENT;
				}
				if (lineNumber >= nOfLines) {
					currentOffset = end;
				} else {
					currentOffset = currentDoc.getLineOffset(lineNumber);
				}
				return result;
			} catch (BadLocationException e) {
				currentOffset = end;
				return DEFAULT;
			}
		}

		@Override
		public int getTokenOffset() {
			return tokenStart;
		}

		@Override
		public int getTokenLength() {
			return currentOffset - tokenStart;
		}

		@Override
		public void setPartialRange(IDocument document, int offset, int length,
				String contentType, int partitionOffset) {
			setRange(document, offset, length);
		}

		private static boolean isComment(String text, char commentChar) {
			int len = text.length();
			for (int i = 0; i < len; i++) {
				char ch = text.charAt(i);
				if (!Character.isWhitespace(ch)) {
					return ch == commentChar;
				}
			}
			return false;
		}
	}
