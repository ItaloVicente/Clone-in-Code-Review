		super.handleEvent(event);
	}

	protected void handleSourceBrowseButtonPressed() {

		String currentSource = this.sourceNameField.getText();
		DirectoryDialog dialog = new DirectoryDialog(
				sourceNameField.getShell(), SWT.SAVE | SWT.SHEET);
		dialog.setText(SELECT_SOURCE_TITLE);
		dialog.setMessage(SELECT_SOURCE_MESSAGE);
		dialog.setFilterPath(getSourceDirectoryName(currentSource));

		String selectedDirectory = dialog.open();
		if (selectedDirectory != null) {
			if ((getSourceDirectory(selectedDirectory) == null)
					|| selectedDirectory.equals(currentSource)) {
