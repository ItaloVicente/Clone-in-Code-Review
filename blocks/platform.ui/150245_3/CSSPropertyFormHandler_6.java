
		} else {
			String headProperty = propertyToHeadProperty.get(property);
			if (headProperty != null && value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
				Color color = (Color) engine.convert(value, Color.class, form.getDisplay());
				form.setHeadColor(headProperty, color);
			}
