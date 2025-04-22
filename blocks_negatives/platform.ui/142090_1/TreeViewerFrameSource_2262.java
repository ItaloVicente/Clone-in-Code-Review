    /**
     * Connects this source as a listener on the frame list,
     * so that when the current frame changes, the viewer is updated.
     */
    public void connectTo(FrameList frameList) {
        frameList.addPropertyChangeListener(event -> TreeViewerFrameSource.this.handlePropertyChange(event));
    }
