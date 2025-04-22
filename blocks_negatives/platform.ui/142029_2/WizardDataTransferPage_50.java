        displayErrorDialog(message);
    }

    /**
     * Get the title for an error dialog. Subclasses should
     * override.
     */
    protected String getErrorDialogTitle() {
        return IDEWorkbenchMessages.WizardExportPage_internalErrorTitle;
    }
