    private int current;

    /**
     * Creates a new frame list with the given source.
     *
     * @param source the frame source
     */
    public FrameList(IFrameSource source) {
        this.source = source;
        Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME, 0);
        frame.setParent(this);
        frame.setIndex(0);
