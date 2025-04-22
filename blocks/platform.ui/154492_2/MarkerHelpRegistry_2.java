	private IMarkerHelpContextProvider createHelpProvider(IConfigurationElement element) {
		IMarkerHelpContextProvider provider = getHelpProvider(element);
		if (provider == null) {
			try {
				provider = (IMarkerHelpContextProvider) element.createExecutableExtension(ATT_PROVIDER);
			} catch (CoreException e) {
				provider = DUMMY_HELP_PROVIDER;
				Policy.handle(e);
			}
		}
		putHelpProvider(element, provider);
		return provider;
	}

