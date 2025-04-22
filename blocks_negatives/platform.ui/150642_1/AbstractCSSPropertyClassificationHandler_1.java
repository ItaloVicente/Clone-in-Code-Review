		}
		if ("cursor".equals(property)) {
			applyCSSPropertyCursor(element, value, pseudo, engine);
		}
		if ("display".equals(property)) {
			applyCSSPropertyDisplay(element, value, pseudo, engine);
		}
		if ("float".equals(property)) {
			applyCSSPropertyFloat(element, value, pseudo, engine);
		}
		if ("position".equals(property)) {
			applyCSSPropertyPosition(element, value, pseudo, engine);
		}
		if ("visibility".equals(property)) {
			applyCSSPropertyVisibility(element, value, pseudo, engine);
		}
		return false;
