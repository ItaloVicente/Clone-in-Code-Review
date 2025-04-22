    /**
     * Restore IPersistableElements from the specified memento.
     *
     * @param memento memento to restore elements from
     * @return list of restored elements. May be empty.
     */
    private List<IAdaptable> restoreElements(IMemento memento) {
        IMemento[] elementMem = memento.getChildren(TAG_ELEMENT);
        List<IAdaptable> elements = new ArrayList<IAdaptable>(elementMem.length);
