    /**
     * Creates a wizard for importing resources into the workspace from
     * the file system.
     */
    public FileSystemImportWizard() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = workbenchSettings
        if (section == null) {
