    /**
     * Creates a wizard for exporting workspace preferences to the local file system.
     */
    public PreferencesExportWizard() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = workbenchSettings
        if (section == null) {
