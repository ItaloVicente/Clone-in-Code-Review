	protected CSS2FontProperties fontProperties(String family, Object size, Object style) throws Exception {
		CSS2FontProperties result = mock(CSS2FontProperties.class);
		doReturn(valueImpl(family)).when(result).getFamily();
		doReturn(valueImpl(size)).when(result).getSize();
		doReturn(valueImpl(style)).when(result).getStyle();
		return result;					
	}	
	
	private CSSValueImpl valueImpl(final Object value) {
		if (value != null) {
			return new CSSValueImpl() {
				@Override
				public String getCssText() {
					return value.toString();
