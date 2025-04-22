    /**
     * Constructs a new frame source for the specified resource navigator.
     *
     * @param navigator the resource navigator
     */
    public NavigatorFrameSource(ResourceNavigator navigator) {
        super(navigator.getTreeViewer());
        this.navigator = navigator;
    }
