        MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(), IDEWorkbenchMessages.WizardExportPage_errorDialogTitle, message, SWT.SHEET);
    }

    /**
     * Displays a description message that indicates a selection of resources
     * of the specified size.
     *
     * @param selectedResourceCount the resource selection size to display
     */
    protected void displayResourcesSelectedCount(int selectedResourceCount) {
        if (selectedResourceCount == 1) {
