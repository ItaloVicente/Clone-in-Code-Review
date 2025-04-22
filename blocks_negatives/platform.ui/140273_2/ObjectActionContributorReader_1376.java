    /**
     * Creates popup menu contributor from this element.
     */
    protected void processObjectContribution(IConfigurationElement element) {
        String objectClassName = element.getAttribute(IWorkbenchRegistryConstants.ATT_OBJECTCLASS);
        if (objectClassName == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_OBJECTCLASS);
            return;
        }
