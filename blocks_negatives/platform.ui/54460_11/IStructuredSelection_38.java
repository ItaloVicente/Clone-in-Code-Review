    /**
     * Returns the elements in this selection as a <code>List</code>.
     * <strong>Note</strong> In the default implementation of {@link #toList()} in
     * {@link StructuredSelection} the returned list is not a copy of the elements of the
     * receiver and modifying it will modify the contents of the selection.
     *
     * @return the selected elements as a list
     */
    public List toList();
