    /**
     * Implemented by subclasses to produce child/attribute node iterators.
     * @param elementPointer owning pointer
     * @return NodeIterator
     */
    protected abstract NodeIterator
            getElementNodeIterator(NodePointer elementPointer);
