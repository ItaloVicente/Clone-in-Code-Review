		return projectNameField.getText().trim();
	}

	private String getProjectLocationFieldValue() {
		return locationPathField.getText().trim();
	}

	private void handleLocationBrowseButtonPressed() {
		DirectoryDialog dialog = new DirectoryDialog(locationPathField.getShell(), SWT.SHEET);
		dialog.setMessage(DataTransferMessages.WizardExternalProjectImportPage_directoryLabel);

		String dirName = getProjectLocationFieldValue();
		if (dirName.length() == 0) {
