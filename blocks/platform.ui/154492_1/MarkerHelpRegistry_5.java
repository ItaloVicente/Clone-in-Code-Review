			});
		}
		putGenerator(element, generator);
		return generator;
	}

	private IMarkerResolutionGenerator getGenerator(IConfigurationElement element) {
		synchronized (generatorMap) {
			return generatorMap.get(element);
