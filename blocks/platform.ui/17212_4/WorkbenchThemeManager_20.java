	private EventHandler themeChangedHandler = new EventHandler() {
		public void handleEvent(org.osgi.service.event.Event event) {
			IStylingEngine engine = (IStylingEngine) context.get(IStylingEngine.SERVICE_NAME);
			IThemeRegistry themeRegistry = (IThemeRegistry) context.get(IThemeRegistry.class
					.getName());
			FontRegistry fontRegistry = getCurrentTheme().getFontRegistry();
			ColorRegistry colorRegistry = getCurrentTheme().getColorRegistry();

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
	};

