    }

    /**
     * Answer the element factory for an id, or <code>null</code. if not found.
     * @param targetID
     * @return IElementFactory
     */
    public IElementFactory getElementFactory(String targetID) {

        IExtensionPoint extensionPoint;
        extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
                PI_WORKBENCH, IWorkbenchRegistryConstants.PL_ELEMENT_FACTORY);

        if (extensionPoint == null) {
            WorkbenchPlugin
            return null;
        }

        IConfigurationElement targetElement = null;
        IConfigurationElement[] configElements = extensionPoint
                .getConfigurationElements();
        for (IConfigurationElement configElement : configElements) {
            if (targetID.equals(strID)) {
                targetElement = configElement;
                break;
            }
        }
        if (targetElement == null) {
            return null;
        }

        IElementFactory factory = null;
        try {
            factory = (IElementFactory) createExtension(targetElement, "class"); //$NON-NLS-1$
        } catch (CoreException e) {
            WorkbenchPlugin.log(
                    "Unable to create element factory.", e.getStatus()); //$NON-NLS-1$
            factory = null;
        }
        return factory;
    }

    /**
     * Return the perspective registry.
     * @return IPerspectiveRegistry. The registry for the receiver.
     */
    public IPerspectiveRegistry getPerspectiveRegistry() {
