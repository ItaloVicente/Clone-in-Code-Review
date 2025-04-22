				Set<QuickAccessProvider> doneProviders = new HashSet<>();
				elementsForProviders.forEach((provider, elements) -> {
					if (elements.isEmpty()) {
						doneProviders.add(provider);
					}
				});
				doneProviders.forEach(elementsForProviders::remove);
