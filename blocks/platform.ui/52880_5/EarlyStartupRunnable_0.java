		IConfigurationElement[] configElements = extension.getConfigurationElements();
		if (configElements.length == 0) {
			missingStartupElementMessage("The org.eclipse.ui.IStartup extension from '" + //$NON-NLS-1$
						extension.getNamespaceIdentifier() + "' does not provide a valid '" //$NON-NLS-1$
					+ IWorkbenchConstants.TAG_STARTUP + "' element."); //$NON-NLS-1$
		}
