    /**
     * Returns the child elements of the given parent element.
     * <p>
     * The difference between this method and <code>IStructuredContentProvider.getElements</code>
     * is that <code>getElements</code> is called to obtain the
     * tree viewer's root elements, whereas <code>getChildren</code> is used
     * to obtain the children of a given parent element in the tree (including a root).
     * </p>
     * The result is not modified by the viewer.
     *
     * @param parentElement the parent element
     * @return an array of child elements
     */
    public Object[] getChildren(Object parentElement);
