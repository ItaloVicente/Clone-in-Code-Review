    /**
     * Fires a selection changed event.
     *
     * @param selection the new selection
     */
    protected void fireSelectionChanged(ISelection selection) {
        final SelectionChangedEvent event = new SelectionChangedEvent(this,
                selection);

