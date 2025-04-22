	private StyleRange transformFontStyleToFont(Device layoutDevice, Font cellFont, StyleRange styleRange) {
		if (styleRange.font == null && styleRange.fontStyle > 0) {
			Font baseFont = cellFont != null ? cellFont : layoutDevice.getSystemFont();
			StyleRange newRange = (StyleRange) styleRange.clone();
			newRange.font = styledFonts.computeIfAbsent(baseFont, f -> new HashMap<>())
					.computeIfAbsent(Integer.valueOf(styleRange.fontStyle), s -> {
						FontData[] fontDatas = baseFont.getFontData();
						for (FontData fontData : fontDatas) {
							fontData.setStyle(styleRange.fontStyle);
						}
						return new Font(baseFont.getDevice(), fontDatas);
					});
			return newRange;
		}
		return styleRange;
	}

