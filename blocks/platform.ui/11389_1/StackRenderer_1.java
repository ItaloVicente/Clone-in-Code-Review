	protected Font createFont(Font font, int style) {
		FontData[] fontDataArray = font.getFontData();
		for (FontData fontData : fontDataArray) {
			fontData.setStyle(style);
		}
		return new Font(font.getDevice(), fontDataArray);
	}

