    /**
     * Updates the current listener to one that can handle the drag. There can
     * be many listeners and each listener may be able to handle many <code>TransferData</code>
     * types.  The first listener found that supports one of the <code>TransferData</ode>
     * types specified in the <code>DragSourceEvent</code> will be selected.
     *
     * @param event the drag source event
     */
    private void updateCurrentListener(DragSourceEvent event) {
        currentListener = null;
        if (event.dataType == null) {
