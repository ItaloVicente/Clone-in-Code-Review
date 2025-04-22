				prefs = new HashSet<>();
				final IExtensionRegistry registry = Platform.getExtensionRegistry();
				Set<String> bundleIDs = new HashSet<>();
				String[] themeRelatedExtensionPoints = { "org.eclipse.e4.ui.css.swt.theme", "org.eclipse.ui.themes" };
				for (String extensionPoint : themeRelatedExtensionPoints) {
					IConfigurationElement[] elements = registry.getConfigurationElementsFor(extensionPoint);
					for (IConfigurationElement element : elements) {
						try {
							String nameSpace = element.getNamespaceIdentifier();
							if (nameSpace != null) {
								bundleIDs.add(nameSpace);
							}
						} catch (InvalidRegistryObjectException e) {
							Activator.log(LogService.LOG_ERROR, e.getMessage(), e);
						}
					}
				}
				for (String bundleId : bundleIDs) {
					if (bundleId != null) {
						prefs.add(InstanceScope.INSTANCE.getNode(bundleId));
