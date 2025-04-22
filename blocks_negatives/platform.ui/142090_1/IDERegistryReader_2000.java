    /**
     * The constructor.
     */
    protected IDERegistryReader() {
    }

    /**
     * This method extracts description as a subelement of
     * the given element.
     * @return description string if defined, or empty string
     * if not.
     */
    protected String getDescription(IConfigurationElement config) {
        IConfigurationElement[] children = config.getChildren(TAG_DESCRIPTION);
        if (children.length >= 1) {
            return children[0].getValue();
        }
    }

    /**
     * Logs the error in the workbench log using the provided
     * text and the information in the configuration element.
     */
    protected void logError(IConfigurationElement element, String text) {
