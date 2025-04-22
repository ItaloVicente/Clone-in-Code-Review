	private IUriSchemeHandler getHandlerFromExtensionPoint(String uriScheme) throws CoreException {
		IConfigurationElement[] elements = getOrReadConfigurationElements();

		for (IConfigurationElement element : elements) {
			if (uriScheme.equals(element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME))) {
				return createExecutableSchemeHandler(element);
			}
		}
		return null;
	}

	private IConfigurationElement[] getOrReadConfigurationElements() {
		if (this.configurationElements == null) {
			IExtensionRegistry registry = RegistryFactory.getRegistry();
			this.configurationElements = registry.getConfigurationElementsFor(EXT_POINT_ID_URI_SCHEME_HANDLERS);
		}
		return configurationElements;
	}

	private IUriSchemeHandler createExecutableSchemeHandler(IConfigurationElement element) throws CoreException {
		Object executableExtension = element.createExecutableExtension(EXT_POINT_ATTRIBUTE_CLASS);
		if (executableExtension instanceof IUriSchemeHandler) {
			return (IUriSchemeHandler) executableExtension;
		}
		throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID,
	}
