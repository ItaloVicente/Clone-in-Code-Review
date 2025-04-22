	protected void setRangeAndColor(IDocument document, int offset, int length,
			Color color) {
		Assert.isTrue(document == viewer.getDocument());
		this.endOfRange = offset + length;
		this.currentOffset = offset;
		this.tokenStart = -1;
		hyperlinkToken = new Token(
				new HyperlinkDamagerRepairer.HyperlinkTextAttribute(color));
	}
