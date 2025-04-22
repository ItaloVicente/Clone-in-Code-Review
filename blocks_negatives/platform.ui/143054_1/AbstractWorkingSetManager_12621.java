    /**
     * Recreates a working set from the persistence store.
     *
     * @param memento the persistence store
     * @return the working set created from the memento or null if
     * 	creation failed.
     */
    protected IWorkingSet restoreWorkingSet(final IMemento memento) {
        String factoryID = memento
                .getString(IWorkbenchConstants.TAG_FACTORY_ID);

        if (factoryID == null) {
            factoryID = AbstractWorkingSet.FACTORY_ID;
        }
        final IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(
                factoryID);
        if (factory == null) {
            WorkbenchPlugin
            return null;
        }
