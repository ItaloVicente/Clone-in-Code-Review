	}

	protected void createDirectoryStructureOptions(Composite optionsGroup, Font font) {
		createDirectoryStructureButton = new Button(optionsGroup, SWT.RADIO
				| SWT.LEFT);
		createDirectoryStructureButton.setText(DataTransferMessages.FileExport_createDirectoryStructure);
		createDirectoryStructureButton.setSelection(false);
		createDirectoryStructureButton.setFont(font);

		createSelectionOnlyButton = new Button(optionsGroup, SWT.RADIO
				| SWT.LEFT);
		createSelectionOnlyButton.setText(DataTransferMessages.FileExport_createSelectedDirectories);
		createSelectionOnlyButton.setSelection(true);
		createSelectionOnlyButton.setFont(font);
	}

	protected void createOverwriteExisting(Group optionsGroup, Font font) {
		overwriteExistingFilesCheckbox = new Button(optionsGroup, SWT.CHECK
				| SWT.LEFT);
		overwriteExistingFilesCheckbox.setText(DataTransferMessages.ExportFile_overwriteExisting);
		overwriteExistingFilesCheckbox.setFont(font);
	}

