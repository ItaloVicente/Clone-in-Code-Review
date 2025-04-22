
		private void addNewDefinitions(IStylingEngine engine, ThemeRegistry themeRegistry,
				FontRegistry fontRegistry, ColorRegistry colorRegistry) {
			ThemesExtension themesExtension = new ThemesExtension();
			engine.style(themesExtension);

			for (IThemeElementDefinitionOverridable<?> definition : themesExtension
					.getDefinitions()) {				
				engine.style(definition);
				if (definition.isOverridden() && definition instanceof FontDefinition) {
					addFontDefinition((FontDefinition) definition, themeRegistry, fontRegistry);
				} else if (definition.isOverridden() && definition instanceof ColorDefinition) {
					addColorDefinition((ColorDefinition) definition, themeRegistry, colorRegistry);
				}
			}

		}

		private void addFontDefinition(FontDefinition definition, ThemeRegistry themeRegistry,
				FontRegistry fontRegistry) {
			themeRegistry.add(definition);
			fontRegistry.put(definition.getId(), definition.getValue());
		}

		private void addColorDefinition(ColorDefinition definition, ThemeRegistry themeRegistry,
				ColorRegistry colorRegistry) {
			themeRegistry.add(definition);
			colorRegistry.put(definition.getId(), definition.getValue());
		}
