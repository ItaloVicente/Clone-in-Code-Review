
    /**
     * Resource listener that reacts to new projects (and associated natures)
     * coming into the workspace.
     */
    private IResourceChangeListener listener;

    /**
     * Mapping from composite nature ID to IPluginContribution.  Used for proper
     * activity mapping of natures.
     */
