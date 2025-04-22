    }

    /**
     * Updates the working registry.
     * @param definition
     * @param newRGB
     */
    protected void setRegistryValue(ColorDefinition definition, RGB newRGB) {
        colorRegistry.put(definition.getId(), newRGB);
    }

    protected void setRegistryValue(FontDefinition definition, FontData[] datas) {
        fontRegistry.put(definition.getId(), datas);
    }

    /**
     * Returns the preview for the category.
     * @param category the category
     * @return the preview for the category, or its ancestors preview if it does not have one.
     */
    private IThemePreview getThemePreview(ThemeElementCategory category) throws CoreException {
        IThemePreview preview = category.createPreview();
        if (preview != null)
