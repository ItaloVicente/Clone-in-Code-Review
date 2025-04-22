            if (!directory.mkdirs()) {
                displayErrorDialog(DataTransferMessages.DataTransfer_directoryCreationError);
                giveFocusToDestination();
                return false;
            }
        }

        return true;
    }

    /**
     *	If the target for export does not exist then attempt to create it.
     *	Answer a boolean indicating whether the target exists (ie.- if it
     *	either pre-existed or this method was able to create it)
     *
     *	@return boolean
     */
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
