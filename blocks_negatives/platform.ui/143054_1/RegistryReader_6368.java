        }
    }

    /**
     * Read one extension by looping through its
     * configuration elements.
     */
    protected void readExtension(IExtension extension) {
        readElements(extension.getConfigurationElements());
    }

    /**
     *	Start the registry reading process using the
     * supplied plugin ID and extension point.
     *
     * @param registry the registry to read from
     * @param pluginId the plugin id of the extenion point
     * @param extensionPoint the extension point id
     */
    public void readRegistry(IExtensionRegistry registry, String pluginId,
            String extensionPoint) {
        IExtensionPoint point = registry.getExtensionPoint(pluginId,
                extensionPoint);
        if (point == null) {
