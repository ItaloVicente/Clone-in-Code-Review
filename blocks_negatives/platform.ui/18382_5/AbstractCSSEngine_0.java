				if (newValue != null) {
					if (resourcesRegistry != null) {
						if (key != null)
							resourcesRegistry.registerResource(toType, key,
									newValue);
					}
				}
