		}
	}

	protected void readExtension(IExtension extension) {
		readElements(extension.getConfigurationElements());
	}

	protected void readRegistry(IExtensionRegistry registry, String pluginId,
			String extensionPoint) {
		String pointId = pluginId + "-" + extensionPoint; //$NON-NLS-1$
		IExtension[] extensions = extensionPoints.get(pointId);
		if (extensions == null) {
			IExtensionPoint point = registry.getExtensionPoint(pluginId,
					extensionPoint);
			if (point == null) {
