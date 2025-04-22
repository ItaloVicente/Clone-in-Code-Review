        this.value = value;
        updateContents(value);
    }

    /**
     * Returns the default label widget created by <code>createContents</code>.
     *
     * @return the default label widget
     */
    protected Label getDefaultLabel() {
        return defaultLabel;
    }

    /**
     * Opens a dialog box under the given parent control and returns the
     * dialog's value when it closes, or <code>null</code> if the dialog
     * was canceled or no selection was made in the dialog.
     * <p>
     * This framework method must be implemented by concrete subclasses.
     * It is called when the user has pressed the button and the dialog
     * box must pop up.
     * </p>
     *
     * @param cellEditorWindow the parent control cell editor's window
     *   so that a subclass can adjust the dialog box accordingly
     * @return the selected value, or <code>null</code> if the dialog was
     *   canceled or no selection was made in the dialog
     */
    protected abstract Object openDialogBox(Control cellEditorWindow);

    /**
     * Updates the controls showing the value of this cell editor.
     * <p>
     * The default implementation of this framework method just converts
     * the passed object to a string using <code>toString</code> and
     * sets this as the text of the label widget.
     * </p>
     * <p>
     * Subclasses may reimplement.  If you reimplement this method, you
     * should also reimplement <code>createContents</code>.
     * </p>
     *
     * @param value the new value of this cell editor
     */
    protected void updateContents(Object value) {
        if (defaultLabel == null) {
