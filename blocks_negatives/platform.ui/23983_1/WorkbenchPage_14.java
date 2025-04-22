    }

    /**
     * Implements IWorkbenchPage
     * 
     * @see org.eclipse.ui.IWorkbenchPage#getWorkingSet()
     * @since 2.0
     * @deprecated individual views should store a working set if needed
     */
    public IWorkingSet getWorkingSet() {
        return workingSet;
    }

    /**
     * @see IWorkbenchPage
     */
    public void hideActionSet(String actionSetID) {
