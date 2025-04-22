    /**
     * Creates a wizard for importing resources into the workspace from
     * the file system.
     */
    public PreferencesImportWizard() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = workbenchSettings
        if (section == null) {
