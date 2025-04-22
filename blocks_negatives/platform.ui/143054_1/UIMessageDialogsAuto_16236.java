    /*
     * Convenience method simliar to org.eclipse.jface.dialogs.MessageDialog::openInformation.
     * The method will return the dialog instead of opening.
     * @param title the dialog's title, or <code>null</code> if none.
     * @param message the message.
     * @return MessageDialog the information dialog.
     */
    private MessageDialog getInformationDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
