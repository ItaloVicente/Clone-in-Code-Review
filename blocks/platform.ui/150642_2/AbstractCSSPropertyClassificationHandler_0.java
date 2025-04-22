		} else
			if ("cursor".equals(property)) {
				applyCSSPropertyCursor(element, value, pseudo, engine);
			} else if ("display".equals(property)) {
				applyCSSPropertyDisplay(element, value, pseudo, engine);
			} else if ("float".equals(property)) {
				applyCSSPropertyFloat(element, value, pseudo, engine);
			} else if ("position".equals(property)) {
				applyCSSPropertyPosition(element, value, pseudo, engine);
			} else if ("visibility".equals(property)) {
				applyCSSPropertyVisibility(element, value, pseudo, engine);
			} else {
				applied = false;
			}
		return applied;
