		registryChangeListener = new IRegistryChangeListener() {
			@Override
			public void registryChanged(IRegistryChangeEvent event) {
				for (IExtensionDelta delta : event.getExtensionDeltas()) {
					if (delta.getKind() != IExtensionDelta.REMOVED) continue;
					for (IConfigurationElement element : delta.getExtension().getConfigurationElements()) {
						removeElementsNamed(getProviderId(element));
					}
				}
			}

			private String getProviderId(IConfigurationElement element) {
				return element.getNamespaceIdentifier()
						+ "." + element.getAttribute(ExtensionQuickAccessProvider.ATTRIBUTE_ID); //$NON-NLS-1$
			}

			private void removeElementsNamed(String id) {
				Iterator<QuickAccessElement> iterator = previousPicksList.iterator();
				while (iterator.hasNext()) {
					QuickAccessProvider provider = iterator.next().getProvider();
					if (provider instanceof ExtensionQuickAccessProvider) {
						if (((ExtensionQuickAccessProvider) provider).getId() == id) {
							iterator.remove();
							for (QuickAccessElement element : provider.getElements()) {
								elementMap.remove(element.getId());
							}
						}
					}
				}
			}
		};
		Platform.getExtensionRegistry().addRegistryChangeListener(registryChangeListener, "org.eclipse.ui.workbench"); //$NON-NLS-1$
		
		quickAccessContents = new QuickAccessContents(providers, window.getContext()) {
