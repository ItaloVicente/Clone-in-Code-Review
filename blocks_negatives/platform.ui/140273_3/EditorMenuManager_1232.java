    /**
     * Sets the visibility of the manager. If the visibility is <code>true</code>
     * then each item within the manager appears within the parent manager.
     * Otherwise, the items are not visible.
     * <p>
     * If force visibility is <code>true</code>, or grayed out if force visibility is <code>false</code>
     * <p>
     * This is a workaround for the layout flashing when editors contribute
     * large amounts of items.</p>
     *
     * @param visible the new visibility
     * @param forceVisibility whether to change the visibility or just the
     * 		enablement state.
     */
    public void setVisible(boolean visible, boolean forceVisibility) {
        if (visible) {
            if (forceVisibility) {
                if (!enabledAllowed) {
