    /**
     * Sets the working set edited on the page.
     * Implementors should not make a copy of this working set.
     * The passed object can be edited as is and should be
     * returned in getSelection().
     *
     * @param workingSet the working set edited on the page.
     */
    void setSelection(IWorkingSet workingSet);
