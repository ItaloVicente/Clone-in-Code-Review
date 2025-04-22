		Color color = null;
		if (TRANSPARENT_NAME.equals(value.getCssText())) {
			color = CSSSWTColorHelper.getSWTTransparentColor(display);
		} else {
			color = CSSSWTColorHelper.getSWTColor(value, display);
		}

		if (color == null) {
