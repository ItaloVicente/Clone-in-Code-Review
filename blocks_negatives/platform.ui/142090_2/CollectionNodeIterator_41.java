    /**
     * Create a new CollectionNodeIterator.
     * @param pointer collection pointer
     * @param reverse iteration order
     * @param startWith starting pointer
     */
    protected CollectionNodeIterator(
        CollectionPointer pointer,
        boolean reverse,
        NodePointer startWith) {
        this.pointer = pointer;
        this.reverse = reverse;
        this.startWith = startWith;
    }
