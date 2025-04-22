    /**
     * Updates the current listener to one that can handle the drop. There can be many
     * listeners and each listener may be able to handle many <code>TransferData</code>
     * types. The first listener found that can handle a drop of one of the given
     * <code>TransferData</code> types will be selected.
     * If no listener can handle the drag operation the <code>event.detail</code> field
     * is set to <code>DND.DROP_NONE</code> to indicate an invalid drop.
     *
     * @param event the drop target event
     */
    private void updateCurrentListener(DropTargetEvent event) {
        int originalDetail = event.detail;
        event.detail = originalDropType;
