    /**
     * Sets the current listener to <code>listener</code>. Sends the given
     * <code>DropTargetEvent</code> if the current listener changes.
     *
     * @return <code>true</code> if the new listener is different than the previous
     *	<code>false</code> otherwise
     */
    private boolean setCurrentListener(TransferDropTargetListener listener,
            final DropTargetEvent event) {
        if (currentListener == listener) {
