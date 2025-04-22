    /**
     * Creates an import wizard page. If the initial resource selection
     * contains exactly one container resource then it will be used as the default
     * import destination.
     *
     * @param name the name of the page
     * @param selection the current resource selection
     */
    protected WizardImportPage(String name, IStructuredSelection selection) {
        super(name);
