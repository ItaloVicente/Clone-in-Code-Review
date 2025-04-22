    /**
     * Sets the working set that should be edited.
     *
     * @param workingSet the working set that should be edited.
     */
    public void setSelection(IWorkingSet workingSet) {
        this.workingSet = workingSet;
        workingSetEditPage.setSelection(workingSet);
    }
