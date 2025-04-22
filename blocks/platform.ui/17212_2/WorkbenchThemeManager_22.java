	private EventHandler cssThemeChangedHandler = new EventHandler() {
		public void handleEvent(org.osgi.service.event.Event event) {
			IStylingEngine engine = (IStylingEngine) context.get(IStylingEngine.SERVICE_NAME);
			IThemeRegistry themeRegistry = (IThemeRegistry) context.get(IThemeRegistry.class
					.getName());
			FontRegistry fontRegistry = (FontRegistry) context.get(FontRegistry.class.getName());
			ColorRegistry colorRegistry = (ColorRegistry) context
					.get(ColorRegistry.class.getName());

			for (FontDefinition fontDefinition : themeRegistry.getFonts()) {
				engine.style(fontDefinition);
				if (fontDefinition.isOverriden()) {
					fontRegistry.put(fontDefinition.getId(), fontDefinition.getValue());
				}
			}

			for (ColorDefinition colorDefinition : themeRegistry.getColors()) {
				engine.style(colorDefinition);
				if (colorDefinition.isOverriden()) {
					colorRegistry.put(colorDefinition.getId(), colorDefinition.getValue());
				}
			}
		}
	};

