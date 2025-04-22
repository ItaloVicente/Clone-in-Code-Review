    /**
     * Returns the working set which is currently selected.
     *
     * @return the working set which is currently selected.
     */
    public IWorkingSet getWorkingSet() {
        return workingSet;
    }

    /**
     * Sets the current working set.
     *
     * @param newWorkingSet the new working set
     */
    public void setWorkingSet(IWorkingSet newWorkingSet) {
        IWorkingSet oldWorkingSet = workingSet;

        workingSet = newWorkingSet;
        clearWorkingSetAction.setEnabled(newWorkingSet != null);
        editWorkingSetAction.setEnabled(newWorkingSet != null && newWorkingSet.isEditable());

        firePropertyChange(newWorkingSet, oldWorkingSet);
    }

    /**
     * Fire the property change to the updater if there is one available.
     *
     * @param newWorkingSet the new working set
     * @param oldWorkingSet the previous working set
     * @since 3.2
     */
