	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		Button okButton = createButton(parent, IDialogConstants.OK_ID,
				WorkbenchMessages.CustomizePerspectiveDialog_okButtonLabel, true);
		okButton.setFocus();

		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

