    /**
     * Saves the contents of this part to another object.
     * <p>
     * Implementors are expected to open a "Save As" dialog where the user will
     * be able to select a new name for the contents. After the selection is made,
     * the contents should be saved to that new name.  During this operation a
     * <code>IProgressMonitor</code> should be used to indicate progress.
     * </p>
     * <p>
     * If the save is successful, the part fires a property changed event
     * reflecting the new dirty state (<code>PROP_DIRTY</code> property).
     * </p>
     */
    void doSaveAs();
