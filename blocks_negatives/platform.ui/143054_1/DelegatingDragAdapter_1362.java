    /**
     * Removes the given <code>TransferDragSourceListener</code>.
     * Listeners should not be removed while a drag and drop operation is in progress.
     *
     * @param listener the <code>TransferDragSourceListener</code> to remove
     */
    public void removeDragSourceListener(TransferDragSourceListener listener) {
        listeners.remove(listener);
        if (currentListener == listener) {
