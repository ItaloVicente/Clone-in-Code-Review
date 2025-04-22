        }

        viewer.setSelection(new StructuredSelection(selected), true);
    }

    /**
     * Stores the collection of currently-expanded categories in this page's
     * dialog store, in order to recreate this page's state in the next
     * instance of this page.
     */
    protected void storeExpandedCategories(String setting, TreeViewer viewer) {
        Object[] expandedElements = viewer.getExpandedElements();
        List expandedElementPaths = new ArrayList(expandedElements.length);
        for (Object expandedElement : expandedElements) {
            if (expandedElement instanceof IWizardCategory) {
				expandedElementPaths
                        .add(((IWizardCategory) expandedElement)
                                .getPath().toString());
