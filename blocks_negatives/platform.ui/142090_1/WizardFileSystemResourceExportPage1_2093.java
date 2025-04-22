    }

    /**
     * Create the buttons for the group that determine if the entire or
     * selected directory structure should be created.
     * @param optionsGroup
     * @param font
     */
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

    /**
     * Create the button for checking if we should ask if we are going to
     * overwrite existing files.
     * @param optionsGroup
     * @param font
     */
    protected void createOverwriteExisting(Group optionsGroup, Font font) {
        overwriteExistingFilesCheckbox = new Button(optionsGroup, SWT.CHECK
                | SWT.LEFT);
        overwriteExistingFilesCheckbox.setText(DataTransferMessages.ExportFile_overwriteExisting);
        overwriteExistingFilesCheckbox.setFont(font);
    }

    /**
