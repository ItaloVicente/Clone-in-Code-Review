    /**
     * Returns whether this viewer sorter would be affected
     * by a change to the given property of the given element.
     * <p>
     * The default implementation of this method returns <code>false</code>.
     * Subclasses may reimplement.
     * </p>
     *
     * @param element the element
     * @param property the property
     * @return <code>true</code> if the sorting would be affected,
     *    and <code>false</code> if it would be unaffected
     */
    public boolean isSorterProperty(Object element, String property) {
        return false;
    }

    /**
     * Sorts the given elements in-place, modifying the given array.
     * <p>
