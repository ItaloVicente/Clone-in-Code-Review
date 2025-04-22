	protected CSSValueImpl colorProperties(final String value, final short type) {
		return new CSSValueImpl() {
			@Override
			public short getPrimitiveType() {
				return CSSPrimitiveValue.CSS_STRING;
			}
			@Override
			public short getCssValueType() {
				return type;
			}				
			@Override
			public String getStringValue() throws DOMException {
				return value;
			}
		};
