    /**
     * Defines the initial actions for a page.
     * @param layout The layout we are filling
     */
    public void defineActions(IPageLayout layout) {
        layout.addNewWizardShortcut(BasicNewFolderResourceWizard.WIZARD_ID);
        layout.addNewWizardShortcut(BasicNewFileResourceWizard.WIZARD_ID);
