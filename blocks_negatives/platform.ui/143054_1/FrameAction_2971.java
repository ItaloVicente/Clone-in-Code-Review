    /**
     * Disposes this frame action.
     * This implementation removes the property change listener from the frame list.
     */
    public void dispose() {
        frameList.removePropertyChangeListener(propertyChangeListener);
    }
