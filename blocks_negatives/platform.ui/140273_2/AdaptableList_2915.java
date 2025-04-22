    /**
     * Creates a new adaptable list containing the elements of the specified
     * collection, in the order they are returned by the collection's iterator.
     * All of the elements in the list must implement <code>IAdaptable</code>.
     *
     * @param c the initial elements of this list (element type:
     * <code>IAdaptable</code>)
     */
    public AdaptableList(Collection c) {
        this(c.size());
        children.addAll(c);
    }
