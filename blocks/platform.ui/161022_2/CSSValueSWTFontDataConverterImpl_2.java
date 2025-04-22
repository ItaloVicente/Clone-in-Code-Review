		String property = (context instanceof String) ? (String) context : "";
		switch (property) {
		case "font-family":
			return CSSSWTFontHelper.getFontFamily(fontData);
		case "font-size":
			return CSSSWTFontHelper.getFontSize(fontData);
		case "font-style":
			return CSSSWTFontHelper.getFontStyle(fontData);
		case "font-weight":
			return CSSSWTFontHelper.getFontWeight(fontData);
		case "font":
			return CSSSWTFontHelper.getFontComposite(fontData);
		default:
			return null;
