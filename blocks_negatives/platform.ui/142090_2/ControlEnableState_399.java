    /**
     * Recursively reads the enable/disable state for the given window and
     * disables all controls.
     * @param control Control
     */
    private void readStateForAndDisable(Control control) {
        if ((exceptions != null && exceptions.contains(control))) {
