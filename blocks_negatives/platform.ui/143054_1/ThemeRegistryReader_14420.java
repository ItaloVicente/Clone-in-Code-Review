    /**
     * Read a font.
     *
     * @param element the element to read
     * @return the new font
     */
    private FontDefinition readFont(IConfigurationElement element) {
        String name = element.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
