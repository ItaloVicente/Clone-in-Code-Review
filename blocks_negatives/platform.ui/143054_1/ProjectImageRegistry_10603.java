    /**
     * Reads from the plugin registry.
     */
    public void load() {
        ProjectImageRegistryReader reader = new ProjectImageRegistryReader();
        reader.readProjectNatureImages(Platform.getExtensionRegistry(), this);
    }
