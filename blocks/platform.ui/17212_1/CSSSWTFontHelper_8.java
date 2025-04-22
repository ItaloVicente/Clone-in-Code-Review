	
	private static FontData[] findFontDataByDefinition(CSSPrimitiveValue cssFontFamily) {	
		IColorAndFontProvider provider = CSSActivator.getDefault().getColorAndFontProvider();
		FontData[] result = new FontData[0];
		if (provider != null) {
			FontData[] fontData = provider.getFont(normalizeId(cssFontFamily.getStringValue().substring(1)));
			if (fontData != null) {
				result = fontData;
			}
		}
		return result;
	}
	
	private static boolean isValueFromDefinition(CSSPrimitiveValue value) {
		return value != null && VALUE_FROM_FONT_DEFINITION.equals(value.getCssText());
	}
	
	private static boolean isFontFamilyValid(String fontFamily) {
		return fontFamily != null && !fontFamily.trim().isEmpty();
	}
	
