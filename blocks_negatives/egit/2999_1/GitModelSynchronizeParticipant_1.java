		if (!includeResourceModelProvider()) {
			for (ModelProvider provider : avaliableProviders)
				if (provider.getId().equals(WORKSPACE_MODEL_PROVIDER_ID)) {
					providers.add(provider);
					break;
				}

			providers.add(GitChangeSetModelProvider.getProvider());
		} else {
			boolean addGitProvider = true;

			for (ModelProvider provider : avaliableProviders) {
				String providerId = provider.getId();
				providers.add(provider);

				if (addGitProvider
						&& providerId.equals(GitChangeSetModelProvider.ID))
					addGitProvider = false;
			}
