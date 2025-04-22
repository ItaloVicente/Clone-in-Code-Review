		String swtProperty = cssPropertyToSWTProperty.get(property);
		if (swtProperty != null) {
			if ((value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE)) {
				RGBA rgba = CSSSWTColorHelper.getRGBA(value);
				title.setColor(swtProperty, rgba);
			}
		}
