    private WizardDialog exportWizard(IWizardPage page) {
        ExportWizard wizard = new ExportWizard();
        wizard.init(getWorkbench(), null);
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings wizardSettings = workbenchSettings
                .getSection("ExportResourcesAction");
        if (wizardSettings == null) {
