        return element.getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);
    }

    /**
     * Attempt to load the color value from the colorFactory attribute.
     *
     * @param element the element to load from
     * @return the value, or null if it could not be obtained
     */
    private String checkColorFactory(IConfigurationElement element) {
        String value = null;
        if (element.getAttribute(IWorkbenchRegistryConstants.ATT_COLORFACTORY) != null
                || element.getChildren(IWorkbenchRegistryConstants.ATT_COLORFACTORY).length > 0) {
            try {
                IColorFactory factory = (IColorFactory) element
                        .createExecutableExtension(IWorkbenchRegistryConstants.ATT_COLORFACTORY);
                value = StringConverter.asString(factory.createColor());
            } catch (Exception e) {
                WorkbenchPlugin.log(RESOURCE_BUNDLE
                        .getString("Colors.badFactory"), //$NON-NLS-1$
                        WorkbenchPlugin.getStatus(e));
            }
        }
        return value;
    }

    /**
     * Read a theme.
     *
     * @param element the element to read
     * @return the new theme
     */
    protected ThemeDescriptor readTheme(IConfigurationElement element) {
        ThemeDescriptor desc = null;

        String id = element.getAttribute(ThemeDescriptor.ATT_ID);
        if (id == null) {
