	@BeforeClass
	public static void disableSecjureSotoragePasswordProviders() {
		List availableModules = PasswordProviderSelector.getInstance().findAvailableModules(null);
		StringBuffer tmp = new StringBuffer();
		for (Object module : availableModules) {
			ExtStorageModule storageModule = (ExtStorageModule) module;
			tmp.append(storageModule.moduleID).append(",");
		}
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode("org.eclipse.equinox.security");
		node.put(IStorageConstants.DISABLED_PROVIDERS_KEY, tmp.toString());
	}

