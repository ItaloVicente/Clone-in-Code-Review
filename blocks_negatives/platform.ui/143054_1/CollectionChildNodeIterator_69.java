    /**
     * Create a new CollectionChildNodeIterator.
     * @param pointer CollectionPointer
     * @param test child test
     * @param reverse iteration order
     * @param startWith starting pointer
     */
    public CollectionChildNodeIterator(
        CollectionPointer pointer,
        NodeTest test,
        boolean reverse,
        NodePointer startWith) {
        super(pointer, reverse, startWith);
        this.test = test;
    }
