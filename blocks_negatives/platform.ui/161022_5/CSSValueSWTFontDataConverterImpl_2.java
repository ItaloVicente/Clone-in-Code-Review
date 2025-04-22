		if (context instanceof String) {
			String property = (String) context;
			if ("font-family".equals(property)) {
				return CSSSWTFontHelper.getFontFamily(fontData);
			}
			if ("font-size".equals(property)) {
				return CSSSWTFontHelper.getFontSize(fontData);
			}
			if ("font-style".equals(property)) {
				return CSSSWTFontHelper.getFontStyle(fontData);
			}
			if ("font-weight".equals(property)) {
				return CSSSWTFontHelper.getFontWeight(fontData);
			}
			if ("font".equals(property)) {
				return CSSSWTFontHelper.getFontComposite(fontData);
			}
