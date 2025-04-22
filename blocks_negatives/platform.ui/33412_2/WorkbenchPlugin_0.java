    /**
     * Looks up the configuration element with the given id on the given extension point
     * and instantiates the class specified by the class attributes.
     * 
     * @param extensionPointId the extension point id (simple id)
     * @param elementName the name of the configuration element, or <code>null</code>
     *   to match any element
     * @param targetID the target id
     * @return the instantiated extension object, or <code>null</code> if not found
     */
    private Object createExtension(String extensionPointId, String elementName,
            String targetID) {
        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint(PI_WORKBENCH, extensionPointId);
        if (extensionPoint == null) {
            WorkbenchPlugin
            return null;
        }

        IConfigurationElement targetElement = null;
        IConfigurationElement[] elements = extensionPoint
                .getConfigurationElements();
        for (int j = 0; j < elements.length; j++) {
            IConfigurationElement element = elements[j];
            if (elementName == null || elementName.equals(element.getName())) {
                if (targetID.equals(strID)) {
                    targetElement = element;
                    break;
                }
            }
        }
        if (targetElement == null) {
            return null;
        }

        try {
            return createExtension(targetElement, "class"); //$NON-NLS-1$
        } catch (CoreException e) {
                    + ", status: ", e.getStatus()); //$NON-NLS-1$
        }
        return null;
    }

