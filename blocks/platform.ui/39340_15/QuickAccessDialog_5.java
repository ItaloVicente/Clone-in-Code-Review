				registryChangeListener = new IRegistryChangeListener() {
					@Override
					public void registryChanged(IRegistryChangeEvent event) {
						for (IExtensionDelta delta : event.getExtensionDeltas()) {
							if (delta.getKind() != IExtensionDelta.REMOVED)
								continue;
							for (IConfigurationElement element : delta.getExtension().getConfigurationElements()) {
								removeElementsNamed(getProviderId(element));
							}
