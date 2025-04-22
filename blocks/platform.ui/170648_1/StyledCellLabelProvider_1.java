	private StyleRange transformFontStyleToFont(Font cellFont, StyleRange styleRange) {
		if (styleRange.font == null && styleRange.fontStyle > 0) {
			if (cellFont != null) {
				StyleRange newRange = (StyleRange) styleRange.clone();
				newRange.font = styledFonts.computeIfAbsent(cellFont, f -> new HashMap<>())
						.computeIfAbsent(Integer.valueOf(styleRange.fontStyle), s -> {
							FontData[] fontDatas = cellFont.getFontData();
							for (FontData fontData : fontDatas) {
								fontData.setStyle(styleRange.fontStyle);
							}
							return new Font(cellFont.getDevice(), fontDatas);
						});
				return newRange;
			}
		}
		return styleRange;
	}

