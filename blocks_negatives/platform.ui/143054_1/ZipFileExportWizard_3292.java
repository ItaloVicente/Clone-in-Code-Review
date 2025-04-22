    /**
     * Creates a wizard for exporting workspace resources to a zip file.
     */
    public ZipFileExportWizard() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = workbenchSettings
        if (section == null) {
