		List<ModelProvider> providers = new ArrayList<ModelProvider>();
		if (!includeResourceModelProvider())
			providers.add(GitChangeSetModelProvider.getProvider());
		else {
			boolean addGitProvider = true;
			ModelProvider[] enabledProviders = super.getEnabledModelProviders();

			for (ModelProvider provider : enabledProviders) {
				String providerId = provider.getId();
				providers.add(provider);
