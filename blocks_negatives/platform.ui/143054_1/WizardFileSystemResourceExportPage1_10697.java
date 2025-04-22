        saveDirtyEditors();
        saveWidgetValues();

        return executeExportOperation(new FileSystemExportOperation(null,
                resourcesToExport, getDestinationValue(), this));
    }

    /**
     *	Answer the string to display in self as the destination type
     *
     *	@return java.lang.String
     */
    protected String getDestinationLabel() {
        return DataTransferMessages.FileExport_toDirectory;
    }

    /**
     *	Answer the contents of self's destination specification widget
     *
     *	@return java.lang.String
     */
    protected String getDestinationValue() {
        return destinationNameField.getText().trim();
    }

    /**
     *	Set the current input focus to self's destination entry field
     */
    protected void giveFocusToDestination() {
        destinationNameField.setFocus();
    }

    /**
     *	Open an appropriate destination browser so that the user can specify a source
     *	to import from
     */
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

    /**
	 * Updates the content providers to show/hide linked resurces
