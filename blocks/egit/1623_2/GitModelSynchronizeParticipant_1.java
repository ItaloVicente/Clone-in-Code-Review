		ModelProvider[] avaliableProviders = super.getEnabledModelProviders();
		if (!includeResourceModelProvider()) {
			for (ModelProvider provider : avaliableProviders)
				if (provider.getId().equals(WORKSPACE_MODEL_PROVIDER_ID)) {
					providers.add(provider);
					break;
				}

