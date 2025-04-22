        CURRENT_METHOD = method;
    }

    /**
     * @return true if editors should be activated when opened.
     */
    public static boolean activateOnOpen() {
        return getOpenMethod() == DOUBLE_CLICK;
    }

    /*
     * Adds all needed listener to the control in order to implement
     * single-click/double-click strategies.
     */
    private void addListener(Control c) {
        c.addListener(SWT.MouseEnter, eventHandler);
        c.addListener(SWT.MouseExit, eventHandler);
        c.addListener(SWT.MouseMove, eventHandler);
        c.addListener(SWT.MouseDown, eventHandler);
        c.addListener(SWT.MouseUp, eventHandler);
        c.addListener(SWT.KeyDown, eventHandler);
        c.addListener(SWT.Selection, eventHandler);
        c.addListener(SWT.DefaultSelection, eventHandler);
        c.addListener(SWT.Collapse, eventHandler);
        c.addListener(SWT.Expand, eventHandler);
    }

    /*
     * Fire the selection event to all selectionEventListeners
     */
    private void fireSelectionEvent(SelectionEvent e) {
        if (e.item != null && e.item.isDisposed()) {
