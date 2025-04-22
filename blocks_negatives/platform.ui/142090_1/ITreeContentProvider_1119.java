    /**
     * Returns whether the given element has children.
     * <p>
     * Intended as an optimization for when the viewer does not
     * need the actual children.  Clients may be able to implement
     * this more efficiently than <code>getChildren</code>.
     * </p>
     *
     * @param element the element
     * @return <code>true</code> if the given element has children,
     *  and <code>false</code> if it has no children
     */
    public boolean hasChildren(Object element);
