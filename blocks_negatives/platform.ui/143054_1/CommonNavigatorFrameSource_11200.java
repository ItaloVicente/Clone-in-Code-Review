    private CommonNavigator navigator;

    /**
     * Constructs a new frame source for the specified common navigator.
     *
     * @param navigator the common navigator
     */
    public CommonNavigatorFrameSource(CommonNavigator navigator) {
        super(navigator.getCommonViewer());
        this.navigator = navigator;
    }

    /**
     * Returns a new frame.  This implementation extends the super implementation
     * by setting the frame's tool tip text to show the full path for the input
     * element.
     */
    @Override
