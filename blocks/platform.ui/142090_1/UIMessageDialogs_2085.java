				MessageDialog.QUESTION, 0,
						IDialogConstants.YES_LABEL,
						IDialogConstants.YES_TO_ALL_LABEL,
						IDialogConstants.NO_LABEL,
						IDialogConstants.CANCEL_LABEL);
		DialogCheck.assertDialog(dialog);
	}

	public void testErrorClosing() {
		Dialog dialog = getQuestionDialog(WorkbenchMessages.Error,
				WorkbenchMessages.ErrorClosingNoArg);
		DialogCheck.assertDialog(dialog);
	}

	public void testFileExtensionEmpty() {
		Dialog dialog = getInformationDialog("","");
		DialogCheck.assertDialog(dialog);
	}

	public void testFileNameWrong() {
		Dialog dialog = getInformationDialog(
				"Invalid",
				"Invalid file");
		DialogCheck.assertDialog(dialog);
	}

	public void testFileTypeExists() {
		Dialog dialog = getInformationDialog("Exists",
				"Already Exists");
		DialogCheck.assertDialog(dialog);
	}

	public void testInvalidType_1() {
		Dialog dialog = getWarningDialog("Invalid?", "Is this invalid?");
		DialogCheck.assertDialog(dialog);
	}

	public void testInvalidType_2() {
		Dialog dialog = getWarningDialog("Invalid",  "Is this invalid?");
		DialogCheck.assertDialog(dialog);
	}

	public void testMoveReadOnlyCheck() {
		Dialog dialog = new MessageDialog(getShell(), "Move", null, "OK to move",
				MessageDialog.QUESTION, 0,
						IDialogConstants.YES_LABEL,
						IDialogConstants.YES_TO_ALL_LABEL,
						IDialogConstants.NO_LABEL,
						IDialogConstants.CANCEL_LABEL);
		DialogCheck.assertDialog(dialog);
	}

	 public void testNoBuilders() {
	 Dialog dialog = getWarningDialog(
	 WorkbenchMessages.getString("BuildAction.warning"),
	 WorkbenchMessages.getString("BuildAction.noBuilders") );
	 DialogCheck.assertDialog(dialog);
	 }
	 public void testNoGlobalBuildersDialog() {
	 Dialog dialog = getWarningDialog(
	 WorkbenchMessages.getString("GlobalBuildAction.warning"),
	 WorkbenchMessages.getString("GlobalBuildAction.noBuilders") );
	 DialogCheck.assertDialog(dialog);
	 }
	public void testNoPropertyPage() {
		Dialog dialog = getInformationDialog(WorkbenchMessages.PropertyDialog_messageTitle, NLS.bind(WorkbenchMessages.PropertyDialog_noPropertyMessage, (new Object[] { "DummyPropertyPage" })));
		DialogCheck.assertDialog(dialog);
	}

	public void testOperationNotAvailable() {
		Dialog dialog = getInformationDialog(WorkbenchMessages.Information, "Not available");
		DialogCheck.assertDialog(dialog);
	}

	public void testOverwritePerspective() {
		Dialog dialog = new MessageDialog(getShell(), WorkbenchMessages.SavePerspective_overwriteTitle, null,
				NLS.bind(WorkbenchMessages.SavePerspective_overwriteQuestion, (new Object[] { "Dummy Perspective" })),
				MessageDialog.QUESTION, 0,
				IDialogConstants.YES_LABEL,
				IDialogConstants.NO_LABEL,
				IDialogConstants.CANCEL_LABEL);
		DialogCheck.assertDialog(dialog);
	}

	public void testRefreshDeleteProject() {
		Dialog dialog = new MessageDialog(getShell(), "Refresh", null,
				NLS.bind("deleted location", (new Object[] {
