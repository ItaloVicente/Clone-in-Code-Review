				Set<QuickAccessProvider> exhaustedProviders = new HashSet<>();
				elementsForProviders.forEach((provider, elements) -> {
					if (elements.isEmpty()) {
						exhaustedProviders.add(provider);
					}
				});
				exhaustedProviders.forEach(elementsForProviders::remove);
