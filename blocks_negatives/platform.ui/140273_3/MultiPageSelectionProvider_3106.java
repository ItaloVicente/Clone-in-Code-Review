     * Notifies all registered selection changed listeners that the editor's
     * selection has changed. Only listeners registered at the time this method is
     * called are notified.
     *
     * @param event the selection changed event
     */
    public void fireSelectionChanged(final SelectionChangedEvent event) {
        fireEventChange(event, listeners);
    }

    /**
     * Notifies all post selection changed listeners that the editor's
     * selection has changed.
     *
     * @param event the event to propogate.
     * @since 3.2
     */
    public void firePostSelectionChanged(final SelectionChangedEvent event) {
