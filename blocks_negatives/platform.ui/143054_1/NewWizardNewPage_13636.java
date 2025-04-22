            updateWizardSelection((IWizardDescriptor) selectedObject);
        } else {
            selectedElement = null;
            page.setHasPages(false);
            page.setCanFinishEarly(false);
            page.selectWizardNode(null);
            updateDescription(null);
        }
    }

    /**
     * Selects the wizard category and wizard in this page that were selected
     * last time this page was used. If a category or wizard that was
     * previously selected no longer exists then it is ignored.
     */
    protected void selectPreviouslySelected() {
        String selectedId = settings.get(STORE_SELECTED_ID);
        if (selectedId == null) {
