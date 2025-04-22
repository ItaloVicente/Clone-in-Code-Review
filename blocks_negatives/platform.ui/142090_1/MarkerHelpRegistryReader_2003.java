

    /**
     * Get the marker help that is defined in the plugin registry
     * and add it to the given marker help registry.
     * <p>
     * Warning:
     * The marker help registry must be passed in because this
     * method is called during the process of setting up the
     * marker help registry and at this time it has not been
     * safely setup with the plugin.
     * </p>
     */
    public void addHelp(MarkerHelpRegistry registry) {
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        markerHelpRegistry = registry;
        readRegistry(extensionRegistry, IDEWorkbenchPlugin.IDE_WORKBENCH,
                IDEWorkbenchPlugin.PL_MARKER_HELP);
        readRegistry(extensionRegistry, IDEWorkbenchPlugin.IDE_WORKBENCH,
                IDEWorkbenchPlugin.PL_MARKER_RESOLUTION);
    }

    /**
     * Processes one configuration element or child element.
     */
    @Override
