		IConfigurationElement pageElement = null;
		if (myIndex < config.length
			pageElement = config[myIndex];
			myIndex++;
		}
		cloneSourceProvider.add(new CloneSourceProvider(label,
				serverProviderElement, pageElement, hasFixLocation));
		if (myIndex == config.length)
			return;
		addCloneSourceProvider(cloneSourceProvider, config, myIndex);
