    /**
     * Save the specified elements to the given memento.
     * The elements have to be adaptable to IPersistableElement.
     *
     * @param elements elements to persist
     * @param memento memento to persist elements in
     */
    private void saveElements(Object[] elements, IMemento memento) {
        for (Object element : elements) {
