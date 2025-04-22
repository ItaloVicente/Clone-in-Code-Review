	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		Button okButton = getButton(IDialogConstants.OK_ID);
		okButton.setText(MarkerMessages.filtersDialog_applyAndCloseButton);
		setButtonLayoutData(okButton);
	}

