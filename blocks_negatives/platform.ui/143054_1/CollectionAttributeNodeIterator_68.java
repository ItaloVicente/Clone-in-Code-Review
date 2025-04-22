    /**
     * Create a new CollectionAttributeNodeIterator.
     * @param pointer collection pointer
     * @param name attribute name
     */
    public CollectionAttributeNodeIterator(
        CollectionPointer pointer,
        QName name) {
        super(pointer, false, null);
        this.name = name;
    }
