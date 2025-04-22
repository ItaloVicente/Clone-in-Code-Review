    /**
     * Prepare...
     */
    private void prepare() {
        collection = new ArrayList<>();
        NodePointer ptr = (NodePointer) pointer.clone();
        int length = ptr.getLength();
        for (int i = 0; i < length; i++) {
            ptr.setIndex(i);
            NodePointer elementPointer = ptr.getValuePointer();
            NodeIterator iter = getElementNodeIterator(elementPointer);
