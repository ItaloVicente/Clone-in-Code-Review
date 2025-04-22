	public QuickAccessContents(QuickAccessProvider[] providers, IEclipseContext context) {
		baseProviders = providers;
		this.context = context;
		computeProviders();

		Platform.getExtensionRegistry().addRegistryChangeListener(registryChangeListener, "org.eclipse.ui.workbench"); //$NON-NLS-1$
	}

	private void computeProviders() {
		List<QuickAccessProvider> extensionProviders = new ArrayList<>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry.getConfigurationElementsFor(QUICK_ACCESS_EXTENSION_POINT);
		for (IConfigurationElement element : config) {
			extensionProviders.add(new ExtensionQuickAccessProvider(element, context));
		}

		providers = new QuickAccessProvider[baseProviders.length + extensionProviders.size()];
		System.arraycopy(baseProviders, 0, providers, 0, baseProviders.length);
		for (int index = 0; index < extensionProviders.size(); index++)
			providers[index + baseProviders.length] = extensionProviders.get(index);
