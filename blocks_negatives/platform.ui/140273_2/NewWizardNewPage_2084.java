    }

    /**
     * Returns the single selected object contained in the passed
     * selectionEvent, or <code>null</code> if the selectionEvent contains
     * either 0 or 2+ selected objects.
     */
    protected Object getSingleSelection(IStructuredSelection selection) {
        return selection.size() == 1 ? selection.getFirstElement() : null;
    }

    /**
     * Set self's widgets to the values that they held last time this page was
     * open
     *
     */
    protected void restoreWidgetValues() {
        expandPreviouslyExpandedCategories();
        selectPreviouslySelected();
    }

    /**
     * Store the current values of self's widgets so that they can be restored
     * in the next instance of self
     *
     */
    public void saveWidgetValues() {
        storeExpandedCategories();
        storeSelectedCategoryAndWizard();
    }

    /**
     * The user selected either new wizard category(s) or wizard element(s).
     * Proceed accordingly.
     *
     * @param selectionEvent ISelection
     */
    @Override
