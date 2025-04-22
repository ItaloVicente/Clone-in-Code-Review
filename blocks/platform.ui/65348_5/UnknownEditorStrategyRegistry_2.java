	public static boolean isInteractive(String strategyId) {
		if (strategyId == null) {
			return false;
		}
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = extRegistry.getConfigurationElementsFor(EXTENSION_POINT_ID);
		if (extensions != null) {
			for (IConfigurationElement extension : extensions) {
				if (strategyId.equals(readAttribute(extension, "id"))) { //$NON-NLS-1$
					return Boolean.parseBoolean(readAttribute(extension, "interactive")); //$NON-NLS-1$
				}
			}
		}
		return false;
	}

