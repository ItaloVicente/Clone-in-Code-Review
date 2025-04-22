    /**
     * Handles a property change event from the frame list.
     * Calls <code>frameChanged</code> when the current frame changes.
     */
    protected void handlePropertyChange(PropertyChangeEvent event) {
        if (FrameList.P_CURRENT_FRAME.equals(event.getProperty())) {
            frameChanged((TreeFrame) event.getNewValue());
        }
    }
