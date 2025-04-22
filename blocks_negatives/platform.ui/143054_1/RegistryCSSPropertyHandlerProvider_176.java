	public RegistryCSSPropertyHandlerProvider(IExtensionRegistry registry,
			String extensionPointId) {
		this.registry = registry;
		configure(extensionPointId);
	}

