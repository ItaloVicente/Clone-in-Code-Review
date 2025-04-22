    /**
     * Creates a new event for the given source and selection.
     *
     * @param source the viewer
     * @param selection the selection
     */
    public OpenEvent(Viewer source, ISelection selection) {
        super(source);
        Assert.isNotNull(selection);
        this.selection = selection;
    }
