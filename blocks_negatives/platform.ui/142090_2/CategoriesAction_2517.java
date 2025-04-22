    /**
     * Creates the Categories action. This action is used to show
     * or hide categories properties.
     * @param viewer the viewer
     * @param name the name
     */
    public CategoriesAction(PropertySheetViewer viewer, String name) {
        super(viewer, name);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(this, IPropertiesHelpContextIds.CATEGORIES_ACTION);
    }
