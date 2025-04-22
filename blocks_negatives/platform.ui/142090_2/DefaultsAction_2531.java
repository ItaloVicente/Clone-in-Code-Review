    /**
     * Create the Defaults action. This action is used to set
     * the properties back to their default values.
     *
     * @param viewer the viewer
     * @param name the name
     */
    public DefaultsAction(PropertySheetViewer viewer, String name) {
        super(viewer, name);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
