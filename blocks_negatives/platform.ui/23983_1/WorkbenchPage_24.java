    /**
     * Sets the active working set for the workbench page. Notifies property
     * change listener about the change.
     * 
     * @param newWorkingSet
     *            the active working set for the page. May be null.
     * @since 2.0
     * @deprecated individual views should store a working set if needed
     */
    public void setWorkingSet(IWorkingSet newWorkingSet) {
        IWorkingSet oldWorkingSet = workingSet;

        workingSet = newWorkingSet;
        if (oldWorkingSet != newWorkingSet) {
            firePropertyChange(CHANGE_WORKING_SET_REPLACE, oldWorkingSet,
                    newWorkingSet);
        }
        if (newWorkingSet != null) {
            WorkbenchPlugin.getDefault().getWorkingSetManager()
                    .addPropertyChangeListener(workingSetPropertyChangeListener);
        } else {
            WorkbenchPlugin.getDefault().getWorkingSetManager()
                    .removePropertyChangeListener(workingSetPropertyChangeListener);
        }
    }

    /**
     * @see IWorkbenchPage
     */
    public void showActionSet(String actionSetID) {
    	 Perspective persp = getActivePerspective();
         if (persp != null) {
             ActionSetRegistry reg = WorkbenchPlugin.getDefault()
                  .getActionSetRegistry();
             
             IActionSetDescriptor desc = reg.findActionSet(actionSetID);
             if (desc != null) {
                 persp.addActionSet(desc);
                 legacyWindow.updateActionSets();
                 legacyWindow.firePerspectiveChanged(this, getPerspective(),
                         CHANGE_ACTION_SET_SHOW);
             }
         }
    }

    /**
     * See IWorkbenchPage.
     */
    public IViewPart showView(String viewID) throws PartInitException {
        return showView(viewID, null, VIEW_ACTIVATE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPage#showView(java.lang.String,
     *      java.lang.String, int)
     */
