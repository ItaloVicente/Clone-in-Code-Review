
	protected CSSValueImpl colorValue(String value) {
		return colorValue(value, CSSValue.CSS_PRIMITIVE_VALUE);
	}

	protected CSSValueImpl colorValue(String value, short type) {
		CSSValueImpl result = mock(CSSValueImpl.class);
		doReturn(CSSPrimitiveValue.CSS_STRING).when(result).getPrimitiveType();
		doReturn(type).when(result).getCssValueType();
		doReturn(value).when(result).getStringValue();
		doReturn(value).when(result).getCssText();
		return result;
