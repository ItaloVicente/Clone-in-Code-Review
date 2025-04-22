	private void checkInput() {
		filter = textFilter.getText().trim();
		Button okButton = getButton(IDialogConstants.OK_ID);
		String errorMessage = null;
		if (!methodNamePattern.matcher(filter).matches()) {
			if (!filter.isEmpty()) {
				errorMessage = Messages.FilterInputDialog_invalid_method_name;
			}
			okButton.setEnabled(false);
		} else {
			okButton.setEnabled(true);
		}
		setErrorMessage(errorMessage);
