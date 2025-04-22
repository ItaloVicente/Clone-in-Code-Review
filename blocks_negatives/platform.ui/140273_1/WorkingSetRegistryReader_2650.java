    /**
     * Reads the working set extensions within a registry.
     *
     * @param in the plugin registry to read from
     * @param out the working set registry to store read entries in.
     */
    public void readWorkingSets(IExtensionRegistry in, WorkingSetRegistry out) {
        registry = out;
        readRegistry(in, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_WORKINGSETS);
    }
