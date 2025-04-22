	public static boolean hasFontDefinitionAsFamily(CSSValue value) {
		if (value instanceof CSS2FontProperties) {
			CSS2FontProperties props = (CSS2FontProperties) value;
			return props.getFamily() != null && props.getFamily().getStringValue().startsWith(FONT_DEFINITION_MARKER);
		}
		return false;
	}	
	
	public static Font getFont(FontByDefinition fontByDefinition) {
		Font font = fontByDefinition.getResource();
		IColorAndFontProvider provider = CSSActivator.getDefault().getColorAndFontProvider();
		if (provider == null) {
			return font;
		}

		FontData[] fontDataFromDefinition = provider.getFont(fontByDefinition.getSymbolicName());
		FontData[] currentFontData = font.getFontData();
		if (!isFontDataValid(fontDataFromDefinition) || !isFontDataValid(currentFontData)) {
			return font;
		}

		String name = fontDataFromDefinition[0].getName();
		int height = fontByDefinition.getHeight() == null? fontDataFromDefinition[0].getHeight(): fontByDefinition.getHeight();
		int style = fontByDefinition.getStyle() == null? fontDataFromDefinition[0].getStyle(): fontByDefinition.getStyle();
		if (height != currentFontData[0].getHeight() ||
			style != currentFontData[0].getStyle() ||
			!name.equals(currentFontData[0].getName())) {
			font = new Font(font.getDevice(), new FontData(name, height, style));
		}
		return font;
	}
	
	private static boolean isFontDataValid(FontData[] fontData) {
		return fontData != null && fontData.length > 0;
	}
	
