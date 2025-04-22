    /**
     * Sets a new selection for this viewer and optionally makes it visible.
     * <p>
     * Subclasses must implement this method.
     * </p>
     *
     * @param selection the new selection
     * @param reveal <code>true</code> if the selection is to be made
     *   visible, and <code>false</code> otherwise
     */
    public abstract void setSelection(ISelection selection, boolean reveal);
