        return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
    }

    /**
     * Answer self's action enabler, creating it first iff necessary
     */
    protected SelectionEnabler getSelectionEnabler() {
        if (selectionEnabler == null) {
