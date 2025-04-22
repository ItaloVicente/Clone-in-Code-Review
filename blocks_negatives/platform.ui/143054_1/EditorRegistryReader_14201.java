    private EditorRegistry editorRegistry;

    /**
     * Get the editors that are defined in the registry
     * and add them to the ResourceEditorRegistry
     *
     * Warning:
     * The registry must be passed in because this method is called during the
     * process of setting up the registry and at this time it has not been
     * safely setup with the plugin.
     */
    protected void addEditors(EditorRegistry registry) {
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        this.editorRegistry = registry;
        readRegistry(extensionRegistry, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_EDITOR);
    }

    /**
     * Implementation of the abstract method that
     * processes one configuration element.
     */
    @Override
