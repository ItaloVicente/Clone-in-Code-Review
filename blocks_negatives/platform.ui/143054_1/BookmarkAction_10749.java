    /**
     * Creates a bookmark action.
     */
    protected BookmarkAction(BookmarkNavigator view, String label) {
        super(view.getViewer(), label);
        this.view = view;
    }
