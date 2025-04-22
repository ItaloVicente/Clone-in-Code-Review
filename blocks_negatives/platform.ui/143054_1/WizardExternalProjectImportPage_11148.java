        return projectNameField.getText().trim();
    }

    /**
     * Returns the value of the project location field
     * with leading and trailing spaces removed.
     *
     * @return the project location directory in the field
     */
    private String getProjectLocationFieldValue() {
        return locationPathField.getText().trim();
    }

    /**
     *	Open an appropriate directory browser
     */
    private void handleLocationBrowseButtonPressed() {
        DirectoryDialog dialog = new DirectoryDialog(locationPathField
                .getShell(), SWT.SHEET);
        dialog.setMessage(DataTransferMessages.WizardExternalProjectImportPage_directoryLabel);

        String dirName = getProjectLocationFieldValue();
        if (dirName.length() == 0) {
