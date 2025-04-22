    /**
     * Returns the category of the given element. The category is a
     * number used to allocate elements to bins; the bins are arranged
     * in ascending numeric order. The elements within a bin are arranged
     * via a second level sort criterion.
     * <p>
     * The default implementation of this framework method returns
     * <code>0</code>. Subclasses may reimplement this method to provide
     * non-trivial categorization.
     * </p>
     *
     * @param element the element
     * @return the category
     */
    public int category(Object element) {
        return 0;
    }

    /**
     * Returns a negative, zero, or positive number depending on whether
     * the first element is less than, equal to, or greater than
     * the second element.
     * <p>
     * The default implementation of this method is based on
     * comparing the elements' categories as computed by the <code>category</code>
     * framework method. Elements within the same category are further
     * subjected to a case insensitive compare of their label strings, either
     * as computed by the content viewer's label provider, or their
     * <code>toString</code> values in other cases. Subclasses may override.
     * </p>
     *
     * @param viewer the viewer
     * @param e1 the first element
     * @param e2 the second element
     * @return a negative number if the first element is less  than the
     *  second element; the value <code>0</code> if the first element is
     *  equal to the second element; and a positive number if the first
     *  element is greater than the second element
     */
    public int compare(Viewer viewer, Object e1, Object e2) {
        int cat1 = category(e1);
        int cat2 = category(e2);

        if (cat1 != cat2) {
