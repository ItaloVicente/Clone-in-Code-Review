		ModelProvider[] enabledProviders = super.getEnabledModelProviders();
		for (int i = 0; i < enabledProviders.length; i++) {
			ModelProvider provider = enabledProviders[i];
			if (provider.getId().equals(GitChangeSetModelProvider.ID))
				return enabledProviders;
		}
