	}

	public IElementFactory getElementFactory(String targetID) {

		IExtensionPoint extensionPoint;
		extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PI_WORKBENCH,
				IWorkbenchRegistryConstants.PL_ELEMENT_FACTORY);

		if (extensionPoint == null) {
			WorkbenchPlugin.log("Unable to find element factory. Extension point: " //$NON-NLS-1$
					+ IWorkbenchRegistryConstants.PL_ELEMENT_FACTORY + " not found"); //$NON-NLS-1$
			return null;
		}

		IConfigurationElement targetElement = null;
		IConfigurationElement[] configElements = extensionPoint.getConfigurationElements();
		for (IConfigurationElement configElement : configElements) {
			String strID = configElement.getAttribute("id"); //$NON-NLS-1$
			if (targetID.equals(strID)) {
				targetElement = configElement;
				break;
			}
		}
		if (targetElement == null) {
			WorkbenchPlugin.log("Unable to find element factory: " + targetID); //$NON-NLS-1$
			return null;
		}

		IElementFactory factory = null;
		try {
			factory = (IElementFactory) createExtension(targetElement, "class"); //$NON-NLS-1$
		} catch (CoreException e) {
			WorkbenchPlugin.log("Unable to create element factory.", e.getStatus()); //$NON-NLS-1$
			factory = null;
		}
		return factory;
	}

	public IPerspectiveRegistry getPerspectiveRegistry() {
