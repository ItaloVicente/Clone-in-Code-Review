        activeTextControl = textControl;
        textControl.addListener(SWT.Activate, textControlListener);
        textControl.addListener(SWT.Deactivate, textControlListener);

        textControl.addKeyListener(keyAdapter);
        textControl.addMouseListener(mouseAdapter);

    }

    /**
     * Dispose of this action handler
     */
    public void dispose() {
        setCutAction(null);
        setCopyAction(null);
        setPasteAction(null);
        setSelectAllAction(null);
        setDeleteAction(null);
    }

    /**
     * Removes a <code>Text</code> control from the handler
     * so that the Cut, Copy, Paste, Delete, and Select All
     * actions are no longer redirected to it when active.
     *
     * @param textControl the inline <code>Text</code> control
     */
    public void removeText(Text textControl) {
        if (textControl == null) {
