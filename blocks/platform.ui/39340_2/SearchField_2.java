	private void addExtensionProviders(List<IQuickAccessProvider> providers, IEclipseContext context) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry.getConfigurationElementsFor("org.eclipse.ui.workbench.quickAccess"); //$NON-NLS-1$
		for (IConfigurationElement element : config) {
			try {
				Object object = element.createExecutableExtension("class");//$NON-NLS-1$
				if (object instanceof IQuickAccessProvider) {
					IQuickAccessProvider provider = (IQuickAccessProvider) object;
					provider.setContext(context);
					providers.add(provider);
				}
			} catch (CoreException e) {
				WorkbenchPlugin.log("Unable to Quick Access extension from: " + element.getNamespaceIdentifier(), e);//$NON-NLS-1$
			}
		}
	}

