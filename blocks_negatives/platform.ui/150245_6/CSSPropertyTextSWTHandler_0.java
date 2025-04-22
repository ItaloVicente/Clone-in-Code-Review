		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color newColor = (Color) engine.convert(value, Color.class, widget
					.getDisplay());
			if (newColor != null && newColor.isDisposed()) {
				return;
			}
