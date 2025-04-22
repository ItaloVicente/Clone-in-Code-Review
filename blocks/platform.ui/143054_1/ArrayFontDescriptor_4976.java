	private FontData[] data;
	private Font originalFont = null;

	public ArrayFontDescriptor(FontData[] data) {
		this.data = data;
	}

	public ArrayFontDescriptor(Font originalFont) {
		this(originalFont.getFontData());
		this.originalFont = originalFont;
	}

	@Override
