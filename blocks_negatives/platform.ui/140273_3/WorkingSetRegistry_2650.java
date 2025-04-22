    /**
     * Loads the working set registry.
     */
    public void load() {
        WorkingSetRegistryReader reader = new WorkingSetRegistryReader();
        reader.readWorkingSets(Platform.getExtensionRegistry(), this);
    }
