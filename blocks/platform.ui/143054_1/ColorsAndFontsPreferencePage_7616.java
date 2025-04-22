						list.add(colorDefinitions[i]);
					}
				}
			}
			{
				FontDefinition[] fontDefinitions = themeRegistry.getFontsFor(currentTheme.getId());
				for (int i = 0; i < fontDefinitions.length; i++) {
					if (!fontDefinitions[i].isEditable()) {
