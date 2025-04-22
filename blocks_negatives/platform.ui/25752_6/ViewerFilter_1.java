    /**
     * Filters the given elements for the given viewer.
     * The input array is not modified.
     * <p>
     * The default implementation of this method calls 
     * {@link #filter(Viewer, Object, Object[])} with the 
     * parent from the path. Subclasses may override
     * </p>
     * @param viewer the viewer
     * @param parentPath the path of the parent element
     * @param elements the elements to filter
     * @return the filtered elements
     * @since 3.2
     */
    public Object[] filter(Viewer viewer, TreePath parentPath, Object[] elements) {
        return filter(viewer, parentPath.getLastSegment(), elements);
    }
    
    /**
     * Returns whether this viewer filter would be affected 
     * by a change to the given property of the given element.
     * <p>
     * The default implementation of this method returns <code>false</code>.
     * Subclasses should reimplement.
     * </p>
     *
     * @param element the element
     * @param property the property
     * @return <code>true</code> if the filtering would be affected,
     *    and <code>false</code> if it would be unaffected
     */
    public boolean isFilterProperty(Object element, String property) {
        return false;
    }
