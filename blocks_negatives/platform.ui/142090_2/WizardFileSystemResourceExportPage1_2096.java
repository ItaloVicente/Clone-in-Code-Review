    protected boolean ensureTargetIsValid(File targetDirectory) {
        if (targetDirectory.exists() && !targetDirectory.isDirectory()) {
            displayErrorDialog(DataTransferMessages.FileExport_directoryExists);
            giveFocusToDestination();
            return false;
        }

        return ensureDirectoryExists(targetDirectory);
    }

    /**
     *  Set up and execute the passed Operation.  Answer a boolean indicating success.
     *
     *  @return boolean
     */
    protected boolean executeExportOperation(FileSystemExportOperation op) {
        op.setCreateLeadupStructure(createDirectoryStructureButton
                .getSelection());
        op.setOverwriteFiles(overwriteExistingFilesCheckbox.getSelection());
