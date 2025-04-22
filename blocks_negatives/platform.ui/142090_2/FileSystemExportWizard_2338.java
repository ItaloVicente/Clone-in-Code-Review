    /**
     * Creates a wizard for exporting workspace resources to the local file system.
     */
    public FileSystemExportWizard() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = workbenchSettings
        if (section == null) {
