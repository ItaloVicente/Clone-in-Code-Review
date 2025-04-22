	protected void processLanguages() {
		languages = new HashMap<>();
		IConfigurationElement[] languageElements = registry.getConfigurationElementsFor(extId);
		for (IConfigurationElement languageElement : languageElements) {
			try {
				languages.put(languageElement.getAttribute("name"), //$NON-NLS-1$
			} catch (InvalidRegistryObjectException | CoreException e) {
				Activator.log(LogService.LOG_ERROR, e.getMessage(), e);
			}
		}
	}

