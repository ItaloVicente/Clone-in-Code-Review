        DialogCheck.assertDialogTexts(dialog);
    }

    public void testErrorClosing() {
        Dialog dialog = getQuestionDialog(WorkbenchMessages.Error,
                WorkbenchMessages.ErrorClosingNoArg);
        DialogCheck.assertDialogTexts(dialog);
    }
    public void testFileExtensionEmpty() {
        Dialog dialog = getInformationDialog(
                "Empty",
                "ExtensionEmptyMessage");
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testFileNameWrong() {
        Dialog dialog = getInformationDialog(
                "InvalidTitle",
               "InvalidMessage");
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testFileTypeExists() {
        Dialog dialog = getInformationDialog(WorkbenchMessages.FileEditorPreference_existsTitle,
                WorkbenchMessages.FileEditorPreference_existsMessage);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testInvalidType_1() {
        Dialog dialog = getWarningDialog("invalidTitle",invalidMessage);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testInvalidType_2() {
        Dialog dialog = getWarningDialog("invalidType", "invalidTypeMessage");
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testMoveReadOnlyCheck() {
        Dialog dialog = new MessageDialog(getShell(), "Move_title", null, ".MoveResourceAction",
                MessageDialog.QUESTION, 0,
                IDialogConstants.YES_LABEL,
                IDialogConstants.YES_TO_ALL_LABEL,
                IDialogConstants.NO_LABEL,
