
	private static class FooterTokenScanner extends HyperlinkTokenScanner {

		private static final Pattern ITALIC_LINE = Pattern

		private final IToken italicToken;

		public FooterTokenScanner(SourceViewerConfiguration configuration,
				ISourceViewer viewer) {
			super(configuration, viewer);
			Object defaults = defaultToken.getData();
			TextAttribute italic;
			if (defaults instanceof TextAttribute) {
				TextAttribute defaultAttribute = (TextAttribute) defaults;
				int style = defaultAttribute.getStyle() ^ SWT.ITALIC;
				italic = new TextAttribute(defaultAttribute.getForeground(),
						defaultAttribute.getBackground(), style,
						defaultAttribute.getFont());
			} else {
				italic = new TextAttribute(null, null, SWT.ITALIC);
			}
			italicToken = new Token(italic);
		}

		@Override
		protected IToken scanToken() {
			try {
				IRegion region = document
						.getLineInformationOfOffset(currentOffset);
				if (currentOffset == region.getOffset()) {
					String line = document.get(currentOffset,
							region.getLength());
					Matcher m = ITALIC_LINE.matcher(line);
					if (m.find()) {
						currentOffset = Math.min(endOfRange,
								currentOffset + region.getLength());
						return italicToken;
					}
				}
			} catch (BadLocationException e) {
			}
			return null;
		}
	}
