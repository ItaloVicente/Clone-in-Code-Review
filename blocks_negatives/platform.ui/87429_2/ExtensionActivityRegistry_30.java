                .addRegistryChangeListener(new IRegistryChangeListener() {
                    @Override
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
