
	public static boolean hasFontDefinitionAsFamily(CSSValue value) {
		if (value instanceof CSS2FontProperties) {
			CSS2FontProperties props = (CSS2FontProperties) value;
			return props.getFamily() != null
					&& props.getFamily().getStringValue()
					.startsWith(FONT_DEFINITION_MARKER);
		}
		return false;
	}

	private static FontData[] findFontDataByDefinition(CSSPrimitiveValue cssFontFamily) {
