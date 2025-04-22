    /**
     * Creates a new adaptable list with the given initial capacity.
     * All of the elements in the list must implement <code>IAdaptable</code>.
     *
     * @param initialCapacity the initial capacity of the list
     */
    public AdaptableList(int initialCapacity) {
        children = new ArrayList(initialCapacity);
    }
