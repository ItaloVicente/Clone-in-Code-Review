			ColorDefinition[] colorDefinitions = themeRegistry.getColorsFor(currentTheme.getId());
			for (ColorDefinition colorDefinition : colorDefinitions) {
				if (!colorDefinition.isEditable()) {
					continue;
				}
				String catId = colorDefinition.getCategoryId();
				if ((catId == null && categoryId == null)
						|| (catId != null && categoryId != null && categoryId.equals(catId))) {
					if (colorDefinition.getDefaultsTo() != null && parentIsInSameCategory(colorDefinition)) {
