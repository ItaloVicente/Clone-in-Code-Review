        if (page != null) {
            page.setWizard(wizard);
            dialog.showPage(page);
        }
        return dialog;
    }

    private WizardDialog importWizard(IWizardPage page) {
        ImportWizard wizard = new ImportWizard();
        wizard.init(getWorkbench(), null);
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings wizardSettings = workbenchSettings
                .getSection("ImportResourcesAction");
        if (wizardSettings == null) {
