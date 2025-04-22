        return true;
    }

    /**
     * Process a show in element.
     */
    private boolean processShowInPart(IConfigurationElement element) {
        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (id != null) {
