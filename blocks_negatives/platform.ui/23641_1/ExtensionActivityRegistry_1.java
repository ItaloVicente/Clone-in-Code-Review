        this.extensionRegistry = extensionRegistry;

        this.extensionRegistry
                .addRegistryChangeListener(new IRegistryChangeListener() {
                    public void registryChanged(
                            IRegistryChangeEvent registryChangeEvent) {
                        IExtensionDelta[] extensionDeltas = registryChangeEvent
                                .getExtensionDeltas(Persistence.PACKAGE_PREFIX,
                                        Persistence.PACKAGE_BASE);

                        if (extensionDeltas.length != 0) {
							try {
                                load();
                            } catch (IOException eIO) {
                            }
						}
                    }
                });

        try {
            load();
        } catch (IOException eIO) {
        }
    }

    private String getNamespace(IConfigurationElement configurationElement) {
        String namespace = null;

        if (configurationElement != null) {
            IExtension extension = configurationElement.getDeclaringExtension();

            if (extension != null) {
