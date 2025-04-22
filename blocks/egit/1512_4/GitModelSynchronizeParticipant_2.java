		for (ModelProvider provider : enabledProviders) {
			String providerId = provider.getId();
			if (!providerId.equals(WORKSPACE_MODEL_PROVIDER)
					|| includeResourceModel)
				providers.add(provider);

			if (addGitProvider
					&& providerId.equals(GitChangeSetModelProvider.ID))
				addGitProvider = false;
