		}
	}

	protected void readExtension(IExtension extension) {
		readElements(extension.getConfigurationElements());
	}

	public void readRegistry(IExtensionRegistry registry, String pluginId, String extensionPoint) {
		IExtensionPoint point = registry.getExtensionPoint(pluginId, extensionPoint);
		if (point == null) {
