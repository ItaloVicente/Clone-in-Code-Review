        removeListenerObject(listener);
    }

    /**
     * Notify property change listeners about a change to the list of
     * working sets.
     *
     * @param changeId one of
     * 	IWorkingSetManager#CHANGE_WORKING_SET_ADD
     * 	IWorkingSetManager#CHANGE_WORKING_SET_REMOVE
     * 	IWorkingSetManager#CHANGE_WORKING_SET_CONTENT_CHANGE
     * 	IWorkingSetManager#CHANGE_WORKING_SET_NAME_CHANGE
     * @param oldValue the removed working set or null if a working set
     * 	was added or changed.
     * @param newValue the new or changed working set or null if a working
     * 	set was removed.
     */
    protected void firePropertyChange(String changeId, Object oldValue,
            Object newValue) {
        final Object[] listeners = getListeners();

        if (listeners.length == 0) {
