    }

    public void testRenameOverwrite() {
        Dialog dialog = getQuestionDialog("Exists",Overwrite);
        DialogCheck.assertDialog(dialog);
    }

    public void testResetPerspective() {
        Dialog dialog = new MessageDialog(getShell(), WorkbenchMessages.ResetPerspective_title, null, NLS.bind(WorkbenchMessages.ResetPerspective_message, (new Object[] { "Dummy Perspective" })),
                MessageDialog.QUESTION, 0,
                        IDialogConstants.OK_LABEL,
                        IDialogConstants.CANCEL_LABEL);
        DialogCheck.assertDialog(dialog);
    }

    public void testSaveAsOverwrite() {
