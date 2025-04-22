				final IExtensionRegistry registry = Platform.getExtensionRegistry();
				IConfigurationElement[] elements = registry
						.getConfigurationElementsFor("org.eclipse.e4.ui.css.swt.theme");
				Set<String> bundleIDs = new HashSet<>();
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

				for (String bundleId : bundleIDs) {
					if (bundleId != null) {
						prefs.add(InstanceScope.INSTANCE.getNode(bundleId));
