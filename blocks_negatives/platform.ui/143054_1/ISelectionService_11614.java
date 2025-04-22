    /**
     * Returns the current selection in the part with the given id.  If the part is not open,
     * or if the selection in the active part is <em>undefined</em> (the active part has no selection provider)
     * the result will be <code>null</code>.
     *
     * @param partId the id of the part
     * @return the current selection, or <code>null</code> if undefined
     * @since 2.0
     */
    public ISelection getSelection(String partId);
