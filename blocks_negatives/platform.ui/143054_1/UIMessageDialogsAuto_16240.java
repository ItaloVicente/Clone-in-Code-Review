        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNoBuilders() {
        Dialog dialog = getWarningDialog("BuildAction_warning", "noBuilders");
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNoGlobalBuildersDialog() {
        Dialog dialog = getWarningDialog("GlobalBuildAction_warning",GlobalBuildAction_noBuilders);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNoPropertyPage() {
        Dialog dialog = getInformationDialog(WorkbenchMessages.PropertyDialog_messageTitle, NLS.bind(WorkbenchMessages.PropertyDialog_noPropertyMessage, (new Object[] { "DummyPropertyPage" })));
        DialogCheck.assertDialogTexts(dialog);
    }


    public void testOperationNotAvailable() {
        Dialog dialog = getInformationDialog(WorkbenchMessages.Information, "operationNotAvailableMessage");
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testOverwritePerspective() {
        Dialog dialog = new MessageDialog(getShell(), WorkbenchMessages.SavePerspective_overwriteTitle, null,
                NLS.bind(WorkbenchMessages.SavePerspective_overwriteQuestion, (new Object[] { "Dummy Perspective" })),
                MessageDialog.QUESTION, 0,
