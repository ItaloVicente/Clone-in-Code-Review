		if (preferenceManager == null) {
			preferenceManager = new WorkbenchPreferenceManager(PREFERENCE_PAGE_CATEGORY_SEPARATOR);

			PreferencePageRegistryReader registryReader = new PreferencePageRegistryReader(
					getWorkbench());
			registryReader.loadFromRegistry(Platform.getExtensionRegistry());
			preferenceManager.addPages(registryReader.getTopLevelNodes());

		}
		return preferenceManager;
