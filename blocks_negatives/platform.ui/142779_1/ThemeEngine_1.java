				for (IResourceLocator l : getResourceLocators(currentTheme
						.getId())) {
					for (CSSEngine engine : cssEngines) {
						engine.getResourcesLocatorManager()
						.unregisterResourceLocator(l);
					}
				}
