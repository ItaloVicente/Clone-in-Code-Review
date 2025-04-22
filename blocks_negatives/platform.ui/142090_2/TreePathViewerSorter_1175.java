     * <p>
     * The default implementation of this method uses the
     * java.util.Arrays#sort algorithm on the given array,
     * calling {@link #compare(Viewer, TreePath, Object, Object)} to compare elements.
     * </p>
     * <p>
     * Subclasses may reimplement this method to provide a more optimized implementation.
     * </p>
     *
     * @param viewer the viewer
     * @param parentPath the parent path of the given elements
     * @param elements the elements to sort
     */
    public void sort(final Viewer viewer, final TreePath parentPath, Object[] elements) {
        Arrays.sort(elements, (a, b) -> TreePathViewerSorter.this.compare(viewer, parentPath, a, b));
    }
