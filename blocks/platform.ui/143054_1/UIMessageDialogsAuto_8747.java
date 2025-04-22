		DialogCheck.assertDialogTexts(dialog);
	}

	public void testRefreshDeleteProject() {
		Dialog dialog = new MessageDialog(getShell(), "RefreshAction_dialogTitle", null,
				"c:\\dummypath\\" + DUMMY_PROJECT,
				MessageDialog.QUESTION, 0,
				IDialogConstants.YES_LABEL,
				IDialogConstants.NO_LABEL);
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testRenameOverwrite() {
		Dialog dialog = getQuestionDialog(".RenameResourceAction_resourceExist",DUMMY_RELATIVE_PATH);
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testResetPerspective() {
		Dialog dialog = new MessageDialog(getShell(), WorkbenchMessages.ResetPerspective_title, null, NLS.bind(WorkbenchMessages.ResetPerspective_message, (new Object[] { "Dummy Perspective" })),
				MessageDialog.QUESTION, 0,
				IDialogConstants.OK_LABEL,
				IDialogConstants.CANCEL_LABEL);
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testSaveAsOverwrite() {
