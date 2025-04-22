    }

    /**
     * Update the dialog's status line to reflect the given status. It is safe to call
     * this method before the dialog has been opened.
     * @param status
     */
    protected void updateStatus(IStatus status) {
        fLastStatus = status;
        if (fStatusLine != null && !fStatusLine.isDisposed()) {
            updateButtonsEnableState(status);
            fStatusLine.setErrorStatus(status);
        }
    }

    /**
     * Update the status of the ok button to reflect the given status. Subclasses
     * may override this method to update additional buttons.
     * @param status
     */
    protected void updateButtonsEnableState(IStatus status) {
        Button okButton = getOkButton();
        if (okButton != null && !okButton.isDisposed()) {
