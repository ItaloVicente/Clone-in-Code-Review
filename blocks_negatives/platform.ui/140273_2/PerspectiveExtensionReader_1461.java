        return true;
    }

    /**
     * Process a wizard shortcut
     */
    private boolean processWizardShortcut(IConfigurationElement element) {
        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (id != null) {
