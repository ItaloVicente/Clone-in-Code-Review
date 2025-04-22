    /**
     * Notifies that the end user has requested applying a value.
     * All cell editors send this notification.
     * <p>
     * The normal reaction is to update the model with the current cell editor value.
     * However, if the value is not valid, it should not be applied.
     * A typical text-based cell editor would send this message
     * when the end user hits Return, whereas other editors would
     * send it whenever their value changes.
     * </p>
     */
    public void applyEditorValue();
