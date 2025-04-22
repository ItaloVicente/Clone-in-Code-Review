	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button okButton = createButton(parent, IDialogConstants.OK_ID,
				WorkbenchMessages.PreferencesDialog_okButton_label, true);
		setOkButton(okButton);
		getShell().setDefaultButton(okButton);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
