		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	private boolean validateFileType() {

		if (filename.length() == 0) {
			setErrorMessage(null);
			return false;
		}

		int index = filename.lastIndexOf('.');
		if (index == filename.length() - 1) {
			if (index == 0 || (index == 1 && filename.charAt(0) == '*')) {
				setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage);
				return false;
			}
		}

		index = filename.indexOf('*');
		if (index > -1) {
			if (filename.length() == 1) {
				setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage);
				return false;
			}
		}
