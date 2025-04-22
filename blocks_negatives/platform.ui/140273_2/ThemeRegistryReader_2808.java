        return new FontDefinition(name, id, defaultMapping, value, categoryId,
                isEditable, description);
    }

    /**
     * Check for platform specific font values.  This will return the
     * "best match" for the current platform.
     *
     * @param elements the elements to check
     * @return the platform specific font, if any
     */
    private String getPlatformSpecificFontValue(IConfigurationElement[] elements) {
        return getFontValue(getBestPlatformMatch(elements));
    }

    /**
     * Gets the font valu from the value attribute.
     *
     * @param element the element to check
     * @return the font string
     */
    private String getFontValue(IConfigurationElement element) {
        if (element == null) {
