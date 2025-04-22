    /*
     * Convenience method simliar to org.eclipse.jface.dialogs.MessageDialog::openConfirm.
     * The method will return the dialog instead of opening.
     * @param title the dialog's title, or <code>null</code> if none.
     * @param message the message.
     * @return Dialog the confirm dialog.
     */
    private MessageDialog getConfirmDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
