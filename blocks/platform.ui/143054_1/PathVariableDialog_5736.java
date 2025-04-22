		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(type == EXISTING_VARIABLE);

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	private boolean validateVariableName() {
		boolean allowFinish = false;

		if (operationMode == EDIT_LINK_LOCATION)
			return true;

		if (validationStatus == IMessageProvider.ERROR) {
