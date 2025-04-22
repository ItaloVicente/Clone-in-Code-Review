        }
        if (frameInput != null) {
            input = frameInput;
        }
        IMemento expandedMem = memento.getChild(TAG_EXPANDED);
        if (expandedMem != null) {
            List<IAdaptable> elements = restoreElements(expandedMem);
            expandedElements = elements.toArray(new Object[elements.size()]);
        } else {
            expandedElements = new Object[0];
        }
        IMemento selectionMem = memento.getChild(TAG_SELECTION);
        if (selectionMem != null) {
            List<IAdaptable> elements = restoreElements(selectionMem);
            selection = new StructuredSelection(elements);
        } else {
            selection = StructuredSelection.EMPTY;
        }
    }

    /**
     * Save the specified elements to the given memento.
     * The elements have to be adaptable to IPersistableElement.
     *
     * @param elements elements to persist
     * @param memento memento to persist elements in
     */
    private void saveElements(Object[] elements, IMemento memento) {
        for (Object element : elements) {
