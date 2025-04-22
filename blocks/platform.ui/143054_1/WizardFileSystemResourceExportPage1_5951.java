	public boolean finish() {
		List resourcesToExport = getWhiteCheckedResources();
		if (!ensureTargetIsValid(new File(getDestinationValue()))) {
			return false;
		}


		saveDirtyEditors();
		saveWidgetValues();

		return executeExportOperation(new FileSystemExportOperation(null,
				resourcesToExport, getDestinationValue(), this));
	}

	protected String getDestinationLabel() {
		return DataTransferMessages.FileExport_toDirectory;
	}

	protected String getDestinationValue() {
		return destinationNameField.getText().trim();
	}

	protected void giveFocusToDestination() {
		destinationNameField.setFocus();
	}

	protected void handleDestinationBrowseButtonPressed() {
		DirectoryDialog dialog = new DirectoryDialog(getContainer().getShell(),
				SWT.SAVE | SWT.SHEET);
		dialog.setMessage(SELECT_DESTINATION_MESSAGE);
		dialog.setText(SELECT_DESTINATION_TITLE);
		dialog.setFilterPath(getDestinationValue());
		String selectedDirectoryName = dialog.open();

		if (selectedDirectoryName != null) {
			setErrorMessage(null);
			setDestinationValue(selectedDirectoryName);
		}
	}

