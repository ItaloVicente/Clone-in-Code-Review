        if (activeEditor == editor) {
            activeEditor.removePropertyChangeListener(cellListener);
            activeEditor = null;
        }

        Control control = editor.getControl();
        if (control != null) {
            controlToEditor.remove(control);
            if (!control.isDisposed()) {
                control.removeListener(SWT.Activate, controlListener);
                control.removeListener(SWT.Deactivate, controlListener);
            }
        }
    }

    /**
     * Sets the default <code>IAction</code> handler for the Copy
     * action. This <code>IAction</code> is run only if no active
     * cell editor control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Copy action, or <code>null</code> if not interested.
     */
    public void setCopyAction(IAction action) {
        if (copyAction == action) {
