        return true;
    }

    /**
     * Process a hidden toolbar item
     */
    private boolean processHiddenToolBarItem(IConfigurationElement element) {
        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (id != null) {
