    /**
     * Returns <code>true</code> if this listener can handle the drop
     * based on the given <code>DropTargetEvent</code>.
     * <p>
     * This method is called by the <code>DelegatingDropAdapter</code> only
     * if the <code>DropTargetEvent</code> contains a transfer data type
     * supported by this listener. The <code>Transfer</code> returned by the
     * <code>#getTransfer()</code> method is used for this purpose.
     * </p>
     *
     * @param event the drop target event
     * @return <code>true</code> if the listener is enabled for the given
     * 	drop target event.
     */
    boolean isEnabled(DropTargetEvent event);
