    /**
     * Constructs a new action for the specified frame list.
     * and adds a property change listener on it.
     *
     * @param frameList the frame list
     */
    protected FrameAction(FrameList frameList) {
        this.frameList = frameList;
        frameList.addPropertyChangeListener(propertyChangeListener);
    }
