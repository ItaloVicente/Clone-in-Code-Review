		for (ModelProvider provider : avaliableProviders)
			if (provider.getId().equals(GitChangeSetModelProvider.ID))
				return avaliableProviders;

		int capacity = avaliableProviders.length + 1;
		ArrayList<ModelProvider> providers = new ArrayList<ModelProvider>(capacity);
		providers.add(GitChangeSetModelProvider.getProvider());
