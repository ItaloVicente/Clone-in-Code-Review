    /**
     * Notifies that the yes button of this dialog has been pressed. Do the same
     * as an OK but set the return code to YES_ID instead.
     */
    protected void yesPressed() {
        okPressed();
        setReturnCode(IDialogConstants.YES_ID);
    }
