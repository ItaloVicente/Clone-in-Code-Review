				getResourceLocators(currentTheme
					.getId()).forEach((l) -> {
						cssEngines.forEach((engine) -> {
							engine.getResourcesLocatorManager()
								.unregisterResourceLocator(l);
					});
				});
