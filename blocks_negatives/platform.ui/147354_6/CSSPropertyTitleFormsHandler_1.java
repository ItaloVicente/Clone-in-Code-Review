		if (BACKGROUND_COLOR_GRADIENT_TITLEBAR_PROPERTY.equalsIgnoreCase(property)) {
			if (control instanceof Section) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					((Section) control).setTitleBarGradientBackground(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {

				}
			}
		} else if (BACKGROUND_COLOR_TITLEBAR_PROPERTY.equalsIgnoreCase(property)) {
			if (control instanceof Section) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					((Section) control).setTitleBarBackground(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
