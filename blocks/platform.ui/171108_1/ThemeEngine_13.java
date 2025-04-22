			this.currentTheme = theme;
			for (CSSEngine engine : cssEngines) {
				engine.reset();
			}

			for (IResourceLocator l : getResourceLocators(theme.getId())) {
