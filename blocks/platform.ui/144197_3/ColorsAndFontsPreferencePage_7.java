			FontDefinition[] fontDefinitions = themeRegistry.getFontsFor(currentTheme.getId());
			for (FontDefinition fontDefinition : fontDefinitions) {
				if (!fontDefinition.isEditable()) {
					continue;
				}
				String catId = fontDefinition.getCategoryId();
				if ((catId == null && categoryId == null)
						|| (catId != null && categoryId != null && categoryId.equals(catId))) {
					if (fontDefinition.getDefaultsTo() != null && parentIsInSameCategory(fontDefinition)) {
