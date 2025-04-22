        Composite right = new Composite(optionsGroup, SWT.NONE);
        right.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
        right.setLayout(new GridLayout(1, true));

        createDirectoryStructureOptions(right, font);

        createDirectoryStructureButton.setSelection(true);
        createSelectionOnlyButton.setSelection(false);
        compressContentsCheckbox.setSelection(true);
    }

    /**
     * Create the buttons for the group that determine if the entire or
     * selected directory structure should be created.
     * @param optionsGroup
     * @param font
     */
    protected void createFileFormatOptions(Composite optionsGroup, Font font) {
        zipFormatButton = new Button(optionsGroup, SWT.RADIO | SWT.LEFT);
        zipFormatButton.setText(DataTransferMessages.ArchiveExport_saveInZipFormat);
        zipFormatButton.setSelection(true);
        zipFormatButton.setFont(font);

        targzFormatButton = new Button(optionsGroup, SWT.RADIO | SWT.LEFT);
        targzFormatButton.setText(DataTransferMessages.ArchiveExport_saveInTarFormat);
        targzFormatButton.setSelection(false);
        targzFormatButton.setFont(font);
    }

    /**
     * Returns a boolean indicating whether the directory portion of the
     * passed pathname is valid and available for use.
     */
    protected boolean ensureTargetDirectoryIsValid(String fullPathname) {
        int separatorIndex = fullPathname.lastIndexOf(File.separator);

        if (separatorIndex == -1) {
