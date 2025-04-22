		if ((value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) &&
				( ((CSSPrimitiveValue) value).getPrimitiveType() == CSSPrimitiveValue.CSS_IDENT) ) {
			String postion = ((CSSPrimitiveValue) value).getStringValue();
			if (postion.equalsIgnoreCase("bottom")) {
				((CTabFolder) control).setTabPosition(SWT.BOTTOM);
			}

			if (postion.equalsIgnoreCase("top")) {
				((CTabFolder) control).setTabPosition(SWT.TOP);
			}
