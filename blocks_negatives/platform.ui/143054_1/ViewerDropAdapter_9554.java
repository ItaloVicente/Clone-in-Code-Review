    public void setFeedbackEnabled(boolean value) {
        feedbackEnabled = value;
    }

    /**
     * Sets whether selection feedback should be provided during dragging.
     *
     * @param value <code>true</code> if selection feedback is desired, and
     *   <code>false</code> if not
     *
     * @since 3.2
     */
    public void setSelectionFeedbackEnabled(boolean value) {
        selectFeedbackEnabled = value;
    }

    /**
     * Sets whether auto scrolling and expanding should be provided during dragging.
     *
     * @param value <code>true</code> if scrolling and expanding is desired, and
     *   <code>false</code> if not
     * @since 2.0
     */
    public void setScrollExpandEnabled(boolean value) {
    	expandEnabled = value;
    	scrollEnabled = value;
    }

    /**
     * Sets whether auto expanding should be provided during dragging.
     *
     * @param value <code>true</code> if expanding is desired, and
     *   <code>false</code> if not
     * @since 3.4
     */
    public void setExpandEnabled(boolean value) {
        expandEnabled = value;
    }

    /**
     * Sets whether auto scrolling should be provided during dragging.
     *
     * @param value <code>true</code> if scrolling is desired, and
     *   <code>false</code> if not
     * @since 3.4
     */
    public void setScrollEnabled(boolean value) {
        scrollEnabled = value;
    }

    /**
     * Validates dropping on the given object. This method is called whenever some
     * aspect of the drop operation changes.
     * <p>
     * Subclasses must implement this method to define which drops make sense.
     * </p>
     *
     * @param target the object that the mouse is currently hovering over, or
     *   <code>null</code> if the mouse is hovering over empty space
     * @param operation the current drag operation (copy, move, etc.)
     * @param transferType the current transfer type
     * @return <code>true</code> if the drop is valid, and <code>false</code>
     *   otherwise
     */
    public abstract boolean validateDrop(Object target, int operation,
            TransferData transferType);
