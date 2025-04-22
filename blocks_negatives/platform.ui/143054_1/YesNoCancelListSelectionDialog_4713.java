    /**
     * Notifies that the no button of this dialog has been pressed.
     * <p>
     * The <code>Dialog</code> implementation of this framework method sets
     * this dialog's return code to <code>IDialogConstants.NO_ID</code> and
     * closes the dialog. Subclasses may override if desired.
     * </p>
     */
    protected void noPressed() {
        setReturnCode(IDialogConstants.NO_ID);
        close();
    }
