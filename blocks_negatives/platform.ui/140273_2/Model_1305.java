    /**
     * Sets the current state of the model.
     *
     * @param newState the new state of the model
     * @param toOmit change listener that should be omitted from change notifications (or null if all
     * listeners should be notified).
     */
    public void setState(Object newState, IChangeListener toOmit) {
        if (areEqual(newState, state)) {
            return;
        }
