	QuickAccessProvider getProvider(String providerId) {
		if (providers == null || providers.length == 0) {
			return null;
		}
		if (providerMap == null || providerMap.size() != providers.length) {
			providerMap = Arrays.stream(providers)
					.collect(Collectors.toMap(QuickAccessProvider::getId, Function.identity()));
		}
		return providerMap.get(providerId);
	}

	QuickAccessProvider getProviderFor(QuickAccessElement quickAccessElement) {
		return elementsToProviders.get(quickAccessElement);
	}

	private QuickAccessMatcher getMatcherFor(QuickAccessElement element) {
		if (!elementsToMatchers.containsKey(element)) {
			elementsToMatchers.put(element, new QuickAccessMatcher(element));
		}
		return elementsToMatchers.get(element);
	}

	void registerProviderFor(QuickAccessElement quickAccessElement, QuickAccessProvider quickAccessProvider) {
		if (quickAccessElement == null || quickAccessProvider == null) {
			return;
		}
		elementsToProviders.put(quickAccessElement, quickAccessProvider);
	}

