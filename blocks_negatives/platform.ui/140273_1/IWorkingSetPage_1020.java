    /**
     * Returns the working set edited or created on the page
     * after the wizard has closed.
     * Returns the working set that was initially set using
     * <code>setSelection</code>if the wizard has not been
     * closed yet.
     * Implementors should return the object set in setSelection
     * instead of making a copy and returning the changed copy.
     *
     * @return the working set edited or created on the page.
     */
    IWorkingSet getSelection();
