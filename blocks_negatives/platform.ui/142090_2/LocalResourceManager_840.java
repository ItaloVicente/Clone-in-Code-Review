    /**
     * Creates a local registry that delegates to the given global registry
     * for all resource allocation and deallocation.
     *
     * @param parentRegistry global registry
     */
    public LocalResourceManager(ResourceManager parentRegistry) {
        this.parentRegistry = parentRegistry;
    }
