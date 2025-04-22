    /**
     * Creates an export wizard page. If the current resource selection
     * is not empty then it will be used as the initial collection of resources
     * selected for export.
     *
     * @param pageName the name of the page
     * @param selection the current resource selection
     */
    protected WizardExportPage(String pageName, IStructuredSelection selection) {
        super(pageName);
        this.currentResourceSelection = selection;
    }
