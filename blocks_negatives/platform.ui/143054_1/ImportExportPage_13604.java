    /**
     * Selects the wizard category and wizard in this page that were selected
     * last time this page was used. If a category or wizard that was
     * previously selected no longer exists then it is ignored.
     */
    protected void selectPreviouslySelected(String setting, IWizardCategory wizardCategories, final TreeViewer viewer) {
        String selectedId = getDialogSettings().get(setting);
        if (selectedId == null) {
