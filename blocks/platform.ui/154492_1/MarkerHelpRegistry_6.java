	private IMarkerResolutionGenerator putGenerator(IConfigurationElement element,
			IMarkerResolutionGenerator generator) {
		synchronized (generatorMap) {
			return generatorMap.put(element, generator);
		}
	}

	private IMarkerHelpContextProvider getHelpProvider(IConfigurationElement element) {
		synchronized (helpProviderMap) {
			return helpProviderMap.get(element);
		}
	}

	private IMarkerHelpContextProvider putHelpProvider(IConfigurationElement element,
			IMarkerHelpContextProvider generator) {
		synchronized (helpProviderMap) {
			return helpProviderMap.put(element, generator);
		}
	}
