    }

    /*
     * Convenience method simliar to org.eclipse.jface.dialogs.MessageDialog::openQuestion.
     * The method will return the dialog instead of opening.
     * @param title the dialog's title, or <code>null</code> if none.
     * @param message the message.
     * @return MessageDialog the question dialog.
     */
    private MessageDialog getQuestionDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
                MessageDialog.QUESTION,0,
                IDialogConstants.YES_LABEL,
                IDialogConstants.NO_LABEL );
    }

    /*
     * Convenience method simliar to org.eclipse.jface.dialogs.MessageDialog::getWarningDialog.
     * The method will return the dialog instead of opening.
     * @param title the dialog's title, or <code>null</code> if none.
     * @param message the message.
     * @return MessageDialog the confirm dialog.
     */
    private MessageDialog getWarningDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
                MessageDialog.WARNING, 0,
                IDialogConstants.OK_LABEL);
    }

    public void testAbortPageFlipping() {
        Dialog dialog = getWarningDialog(JFaceResources
                .getString("AbortPageFlippingDialog.title"), JFaceResources
                .getString("AbortPageFlippingDialog.message"));
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testCopyOverwrite() {
        Dialog dialog = getQuestionDialog("Exists",);
         DialogCheck.assertDialogTexts(dialog);
    }

    public void testDeleteProject() {
        String title = "Project";
        String msg ="";
        Dialog dialog = new MessageDialog(getShell(), title, null, // accept the default window icon
