    private static final String DUMMY_RESOURCE = "Dummy.resource";

    private static final String DUMMY_PROJECT = "DummyProject";

    private static final String DUMMY_ABSOLUTE_PATH = "C:\\Dummypath\\Dummy.resource";

    private static final String DUMMY_RELATIVE_PATH = "\\" + DUMMY_PROJECT
            + "\\" + DUMMY_RESOURCE;

    public UIMessageDialogs(String name) {
        super(name);
    }

    private Shell getShell() {
        return DialogCheck.getShell();
    }

    /*
     * Convenience method similar to org.eclipse.jface.dialogs.MessageDialog::openInformation.
     * The method will return the dialog instead of opening.
     * @param title the dialog's title, or <code>null</code> if none.
     * @param message the message.
     * @return MessageDialog the information dialog.
     */
    private MessageDialog getInformationDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
                MessageDialog.INFORMATION, 0,
                IDialogConstants.OK_LABEL);
    }

    /*
     * Convenience method similar to org.eclipse.jface.dialogs.MessageDialog::openQuestion.
     * The method will return the dialog instead of opening.
     * @param title the dialog's title, or <code>null</code> if none.
     * @param message the message.
     * @return MessageDialog the question dialog.
     */
    private MessageDialog getQuestionDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
                MessageDialog.QUESTION, 0,
                IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL );
    }

    /*
     * Convenience method similar to org.eclipse.jface.dialogs.MessageDialog::getWarningDialog.
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
