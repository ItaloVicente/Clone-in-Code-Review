    /**
     * Removes the given <code>TransferDropTargetListener</code>.
     * Listeners should not be removed while a drag and drop operation is in progress.
     *
     * @param listener the listener to remove
     */
    public void removeDropTargetListener(TransferDropTargetListener listener) {
        if (currentListener == listener) {
