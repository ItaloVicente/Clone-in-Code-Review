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
     */
    protected void readRegistry(IExtensionRegistry registry, String pluginId,
            String extensionPoint) {
        IExtension[] extensions = extensionPoints.get(pointId);
        if (extensions == null) {
            IExtensionPoint point = registry.getExtensionPoint(pluginId,
                    extensionPoint);
            if (point == null) {
