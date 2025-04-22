    /**
     * Create the Filter action. This action is used to show
     * or hide expert properties.
     *
     * @param viewer the viewer
     * @param name the name
     */
    public FilterAction(PropertySheetViewer viewer, String name) {
        super(viewer, name);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
