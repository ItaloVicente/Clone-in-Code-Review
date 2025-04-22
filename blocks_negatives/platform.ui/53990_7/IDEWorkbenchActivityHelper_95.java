                new IRegistryChangeListener() {
                    @Override
					public void registryChanged(IRegistryChangeEvent event) {
                        if (event.getExtensionDeltas(
                                "org.eclipse.core.resources", "natures").length > 0) { //$NON-NLS-1$ //$NON-NLS-2$
							loadNatures();
						}
                    }
                }, "org.eclipse.core.resources"); //$NON-NLS-1$
