    /**
     * Unregisters this action as a listener of the workbench part.
     */
    protected void unregisterSelectionListener(IWorkbenchPart aPart) {
        ISelectionProvider selectionProvider = aPart.getSite()
                .getSelectionProvider();
        if (selectionProvider != null) {
            selectionProvider.removeSelectionChangedListener(this);
        }
    }
