		this(InstanceScope.INSTANCE.getNode(UriSchemeExtensionReader.PLUGIN_ID),
				IUriSchemeExtensionReader.newInstance(), IOperatingSystemRegistration.getInstance());
	}

	AutoRegisterSchemeHandlersJob(IEclipsePreferences preferenceNode, IUriSchemeExtensionReader extensionReader,
			IOperatingSystemRegistration osRegistration) {
