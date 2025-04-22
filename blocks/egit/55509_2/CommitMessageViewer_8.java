	private static class CommitPartitionTokenScanner
			implements IPartitionTokenScanner {

		private static final IToken HEADER = new Token(HEADER_CONTENT_TYPE);

		private static final IToken BODY = new Token(
				IDocument.DEFAULT_CONTENT_TYPE);

		private static final IToken FOOTER = new Token(FOOTER_CONTENT_TYPE);

		private int headerEnd;

		private int footerStart;

		private int currentOffset;

		private int end;

		private int tokenStart;

		@Override
		public void setRange(IDocument document, int offset, int length) {
			if (document instanceof CommitDocument) {
				CommitDocument d = (CommitDocument) document;
				headerEnd = d.getHeaderEnd();
				footerStart = d.getFooterStart();
			} else {
				headerEnd = 0;
				footerStart = document.getLength();
			}
			currentOffset = offset;
			end = offset + length;
			tokenStart = -1;
		}

		@Override
		public IToken nextToken() {
			if (currentOffset < end) {
				tokenStart = currentOffset;
				if (currentOffset < headerEnd) {
					currentOffset = Math.min(headerEnd, end);
					return HEADER;
				} else if (currentOffset < footerStart) {
					currentOffset = Math.min(footerStart, end);
					return BODY;
				} else {
					currentOffset = end;
					return FOOTER;
				}
			}
			return Token.EOF;
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
