				}
			}
		} else if (BORDER_COLOR_TITLEBAR_PROPERTY.equalsIgnoreCase(property)) {
			if (control instanceof Section) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					((Section) control).setTitleBarBorderColor(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
