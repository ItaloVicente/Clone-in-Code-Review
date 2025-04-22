        }

        final StructuredSelection selection = new StructuredSelection(selected);
        filteredTree.getViewer().getControl().getDisplay().asyncExec(() -> filteredTree.getViewer().setSelection(selection, true));
    }

    /**
     * Set the dialog store to use for widget value storage and retrieval
     *
     * @param settings IDialogSettings
     */
    public void setDialogSettings(IDialogSettings settings) {
        this.settings = settings;
    }

    /**
     * Stores the collection of currently-expanded categories in this page's
     * dialog store, in order to recreate this page's state in the next
     * instance of this page.
     */
    protected void storeExpandedCategories() {
        Object[] expandedElements = filteredTree.getViewer().getExpandedElements();
        List expandedElementPaths = new ArrayList(expandedElements.length);
        for (Object expandedElement : expandedElements) {
            if (expandedElement instanceof IWizardCategory) {
				expandedElementPaths
                        .add(((IWizardCategory) expandedElement)
                                .getPath().toString());
