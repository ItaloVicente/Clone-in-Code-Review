    /**
     * Fires out a selection to all listeners.
     */
    public void fireSelection() {
        fireSelection(new SelectionChangedEvent(this, StructuredSelection.EMPTY));
    }
