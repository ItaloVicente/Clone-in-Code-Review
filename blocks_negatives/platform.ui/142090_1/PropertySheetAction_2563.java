    /**
     * Create a PropertySheetViewer action.
     */
    protected PropertySheetAction(PropertySheetViewer viewer, String name) {
        super(name);
        this.id = name;
        this.viewer = viewer;
    }
