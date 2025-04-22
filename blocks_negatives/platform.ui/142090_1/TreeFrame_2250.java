    /**
     * Restore IPersistableElements from the specified memento.
     *
     * @param memento memento to restore elements from
     * @return list of restored elements. May be empty.
     */
    private List restoreElements(IMemento memento) {
        IMemento[] elementMem = memento.getChildren(TAG_ELEMENT);
        List elements = new ArrayList(elementMem.length);
