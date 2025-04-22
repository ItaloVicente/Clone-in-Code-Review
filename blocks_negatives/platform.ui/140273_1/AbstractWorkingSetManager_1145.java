        if (adaptable[0] == null) {
            WorkbenchPlugin
            return null;
        }
        if ((adaptable[0] instanceof IWorkingSet) == false) {
            WorkbenchPlugin
            return null;
        }
        return (IWorkingSet) adaptable[0];
    }

    /**
     * Saves the list of most recently used working sets in the persistence
     * store.
     *
     * @param memento the persistence store
     */
    protected void saveMruList(IMemento memento) {
        Iterator iterator = recentWorkingSets.iterator();

        while (iterator.hasNext()) {
            IWorkingSet workingSet = (IWorkingSet) iterator.next();
            IMemento mruMemento = memento
                    .createChild(IWorkbenchConstants.TAG_MRU_LIST);

            mruMemento.putString(IWorkbenchConstants.TAG_NAME, workingSet
                    .getName());
        }
    }

   /**
     * Restores the list of most recently used working sets from the
     * persistence store.
     *
     * @param memento the persistence store
     */
    protected void restoreMruList(IMemento memento) {
        IMemento[] mruWorkingSets = memento
                .getChildren(IWorkbenchConstants.TAG_MRU_LIST);

        for (int i = mruWorkingSets.length - 1; i >= 0; i--) {
            String workingSetName = mruWorkingSets[i]
                    .getString(IWorkbenchConstants.TAG_NAME);
            if (workingSetName != null) {
                IWorkingSet workingSet = getWorkingSet(workingSetName);
                if (workingSet != null) {
                    internalAddRecentWorkingSet(workingSet);
                }
            }
        }
    }


    /**
     * @see org.eclipse.ui.IWorkingSetManager#createWorkingSetEditWizard(org.eclipse.ui.IWorkingSet)
     * @since 2.1
     */
    @Override
	public IWorkingSetEditWizard createWorkingSetEditWizard(
            IWorkingSet workingSet) {
        String editPageId = workingSet.getId();
        WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
                .getWorkingSetRegistry();
        IWorkingSetPage editPage = null;

        if (editPageId != null) {
            editPage = registry.getWorkingSetPage(editPageId);
        }

