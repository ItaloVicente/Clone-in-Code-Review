    /**
     * Creates a new event for the given source and selection.
     *
     * @param source the selection provider
     * @param selection the selection
     */
    public SelectionChangedEvent(ISelectionProvider source, ISelection selection) {
        super(source);
        Assert.isNotNull(selection);
        this.selection = selection;
    }
