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
				Platform.getExtensionRegistry().addRegistryChangeListener(registryChangeListener,
						"org.eclipse.ui.workbench"); //$NON-NLS-1$

				QuickAccessDialog.this.contents = new QuickAccessContents(providers, model.getContext()) {
