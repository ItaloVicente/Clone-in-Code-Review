        Control control = editor.getControl();
        Assert.isNotNull(control);
        controlToEditor.put(control, editor);
        control.addListener(SWT.Activate, controlListener);
        control.addListener(SWT.Deactivate, controlListener);

        if (control.isFocusControl()) {
            activeEditor = editor;
            editor.addPropertyChangeListener(cellListener);
            updateActionsEnableState();
        }
    }

    /**
     * Disposes of this action handler
     */
    public void dispose() {
        setCutAction(null);
        setCopyAction(null);
        setPasteAction(null);
        setDeleteAction(null);
        setSelectAllAction(null);
        setFindAction(null);
        setUndoAction(null);
        setRedoAction(null);

        Iterator itr = controlToEditor.keySet().iterator();
        while (itr.hasNext()) {
            Control control = (Control) itr.next();
            if (!control.isDisposed()) {
                control.removeListener(SWT.Activate, controlListener);
                control.removeListener(SWT.Deactivate, controlListener);
            }
        }
        controlToEditor.clear();

        if (activeEditor != null) {
			activeEditor.removePropertyChangeListener(cellListener);
