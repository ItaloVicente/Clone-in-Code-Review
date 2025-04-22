    /**
     * Create a descriptor from a configuration element.
     *
     * @param configElement the configuration element
     * @throws CoreException thrown if there is an issue creating the descriptor
     */
    public ActionSetDescriptor(IConfigurationElement configElement)
            throws CoreException {
        super();
        this.configElement = configElement;
        id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        pluginId = configElement.getNamespace();
        label = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
        description = configElement.getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);
        String str = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_VISIBLE);
