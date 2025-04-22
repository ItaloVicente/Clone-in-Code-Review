	public static boolean hasColorDefinitionAsValue(CSSValue value) {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			if (primitiveValue.getPrimitiveType() == CSSPrimitiveValue.CSS_STRING) {
				return hasColorDefinitionAsValue(primitiveValue.getStringValue());
			}
		}
		return false;
	}
	
	public static boolean hasColorDefinitionAsValue(String name) {
		if (name.startsWith(COLOR_DEFINITION_MARKER)) {
			return !name.matches(HEX_COLOR_VALUE_PATTERN);
		}
		return false;
	}
	
