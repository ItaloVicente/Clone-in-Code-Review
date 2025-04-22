    /**
     * Read a color.
     *
     * @param element the element to read
     * @return the new color
     */
    private ColorDefinition readColor(IConfigurationElement element) {
        String name = element.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
