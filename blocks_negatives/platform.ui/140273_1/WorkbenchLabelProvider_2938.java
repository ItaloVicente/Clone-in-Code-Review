    /**
     * Listener that tracks changes to the editor registry and does a full update
     * when it changes, since many workbench adapters derive their icon from the file
     * associations in the registry.
     */
    private IPropertyListener editorRegistryListener = (source, propId) -> {
