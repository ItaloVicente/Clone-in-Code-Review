    /**
     * Registers this action as a listener of the workbench part.
     */
    protected void registerSelectionListener(IWorkbenchPart aPart) {
        ISelectionProvider selectionProvider = aPart.getSite()
                .getSelectionProvider();
        if (selectionProvider != null) {
            selectionProvider.addSelectionChangedListener(this);
            selectionChanged(selectionProvider.getSelection());
        }
    }
