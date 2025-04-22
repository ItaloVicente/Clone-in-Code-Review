		op.setOverwriteResources(overwriteExistingResourcesCheckbox
				.getSelection());
	}

	protected String queryZipFileToImport() {
		FileDialog dialog = new FileDialog(sourceNameField.getShell(), SWT.OPEN | SWT.SHEET);
		dialog.setFilterExtensions(this.fileImportMask);
		dialog.setText(DataTransferMessages.ArchiveImportSource_title);

		String currentSourceString = sourceNameField.getText();
		int lastSeparatorIndex = currentSourceString
				.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1) {
