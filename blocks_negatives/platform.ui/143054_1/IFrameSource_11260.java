    /**
     * Returns a new frame describing the state of the source.
     * If the <code>FULL_CONTEXT</code> flag is specified, then the full
     * context of the source should be captured by the frame.
     * Otherwise, only the visible aspects of the frame, such as the name and tool tip text,
     * will be used.
     *
     * @param whichFrame one of the frame constants defined in this interface
     * @param flags a bit-wise OR of the flag constants defined in this interface
     * @return a new frame describing the current state of the source
     */
    public Frame getFrame(int whichFrame, int flags);
