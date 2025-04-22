	QuickAccessProvider getProvider(String providerId) {
		if (providerMap == null) {
			providerMap = Arrays.stream(providers).collect(Collectors.toMap(QuickAccessProvider::getId, Function.identity()));
		}
		return providerMap.get(providerId);
	}

