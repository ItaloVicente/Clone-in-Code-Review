		private void resetThemeRegistries(ThemeRegistry themeRegistry, FontRegistry fontRegistry,
				ColorRegistry colorRegistry) {
			for (FontDefinition def : themeRegistry.getFonts()) {
				if (def.isOverridden() || def.isAddedByCss()) {
					def.resetToDefaultValue();
					fontRegistry.put(def.getId(), def.getValue() != null ? def.getValue()
							: EMPRY_FONT_DATA_VALUE);
