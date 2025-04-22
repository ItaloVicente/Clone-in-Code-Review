	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		Button okButton = getButton(IDialogConstants.OK_ID);
		okButton.setText(JFaceResources.getString("PreferencesDialog.okButtonLabel")); //$NON-NLS-1$
		setButtonLayoutData(okButton);
	}

