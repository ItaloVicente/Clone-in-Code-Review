	
	public static class WorkbenchThemeChangedHandler implements EventHandler {
		public void handleEvent(org.osgi.service.event.Event event) {
			IStylingEngine engine = getStylingEngine();
			ThemeRegistry themeRegistry = getThemeRegistry();
			FontRegistry fontRegistry = getFontRegistry();
			ColorRegistry colorRegistry = getColorRegistry();

			resetThemeRegistries(themeRegistry);
			overrideAlreadyExistingDefinitions(engine, themeRegistry, fontRegistry, colorRegistry);
			addNewDefinitions(engine, themeRegistry, fontRegistry, colorRegistry);
		}

		protected IStylingEngine getStylingEngine() {
			return (IStylingEngine) getContext().get(IStylingEngine.SERVICE_NAME);
		}

		protected ThemeRegistry getThemeRegistry() {
			return (ThemeRegistry) getContext().get(IThemeRegistry.class.getName());
		}

		protected FontRegistry getFontRegistry() {
			return getCurrentTheme().getFontRegistry();
		}

		protected ColorRegistry getColorRegistry() {
			return getCurrentTheme().getColorRegistry();
		}

		private ITheme getCurrentTheme() {
			return WorkbenchThemeManager.getInstance().getCurrentTheme();
		}

		private IEclipseContext getContext() {
			return WorkbenchThemeManager.getInstance().context;
		}

		private void resetThemeRegistries(ThemeRegistry themeRegistry) {
			for (FontDefinition fontDefinition : themeRegistry.getFonts()) {
				if (fontDefinition.isOverridden()) {
					fontDefinition.resetToDefaultValue();
				}
			}
			for (ColorDefinition colorDefinition : themeRegistry.getColors()) {
				if (colorDefinition.isOverridden()) {
					colorDefinition.resetToDefaultValue();
				}
			}
		}

		private void overrideAlreadyExistingDefinitions(IStylingEngine engine,
				ThemeRegistry themeRegistry, FontRegistry fontRegistry, ColorRegistry colorRegistry) {
			for (FontDefinition fontDefinition : themeRegistry.getFonts()) {
				engine.style(fontDefinition);
				if (fontDefinition.isOverridden()) {
					fontRegistry.put(fontDefinition.getId(), fontDefinition.getValue());
				}
			}
			for (ColorDefinition colorDefinition : themeRegistry.getColors()) {
				engine.style(colorDefinition);
				if (colorDefinition.isOverridden()) {
					colorRegistry.put(colorDefinition.getId(), colorDefinition.getValue());
				}
			}
		}

		private void addNewDefinitions(IStylingEngine engine, ThemeRegistry themeRegistry,
				FontRegistry fontRegistry, ColorRegistry colorRegistry) {
			ThemesExtension themesExtension = createThemesExtension();
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

		protected ThemesExtension createThemesExtension() {
			return new ThemesExtension();
		}
	}

