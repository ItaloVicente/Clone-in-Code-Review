    /**
     * Creates a local registry that wraps the given global registry. Anything
     * allocated by this registry will be automatically cleaned up with the given
     * control is disposed. Note that registries created in this way should not
     * be used to allocate any resource that must outlive the given control.
     *
     * @param parentRegistry global registry that handles resource allocation
     * @param owner control whose disposal will trigger cleanup of everything
     * in the registry.
     */
    public LocalResourceManager(ResourceManager parentRegistry, Control owner) {
        this(parentRegistry);
