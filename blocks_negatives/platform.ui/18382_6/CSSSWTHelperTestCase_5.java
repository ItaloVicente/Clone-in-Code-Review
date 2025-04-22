				return null;
			}			
			
			private CSSValueImpl valueImpl(final Object value) {
				if (value != null) {
					return new CSSValueImpl() {
						@Override
						public String getCssText() {
							return value.toString();
						}
						@Override
						public String getStringValue() {
							return getCssText();
						}
						@Override
						public float getFloatValue(short valueType) throws DOMException {
							return Float.parseFloat(getCssText());
						}
					};
