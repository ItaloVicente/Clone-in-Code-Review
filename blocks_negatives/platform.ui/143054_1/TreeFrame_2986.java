        setName(name);
        setToolTipText(name);
    }

    /**
     * Returns the expanded elements.
     *
     * @return the expanded elements
     */
    public Object[] getExpandedElements() {
        return expandedElements;
    }

    /**
     * Returns the input element.
     *
     * @return the input element
     */
    public Object getInput() {
        return input;
    }

    /**
     * Returns the selection.
     *
     * @return the selection
     */
    public ISelection getSelection() {
        return selection;
    }

    /**
     * Returns the tree viewer.
     *
     * @return the tree viewer
     */
    public AbstractTreeViewer getViewer() {
        return viewer;
    }

    /**
     * Restore IPersistableElements from the specified memento.
     *
     * @param memento memento to restore elements from
     * @return list of restored elements. May be empty.
     */
    private List restoreElements(IMemento memento) {
        IMemento[] elementMem = memento.getChildren(TAG_ELEMENT);
        List elements = new ArrayList(elementMem.length);

        for (IMemento currentMemento : elementMem) {
            String factoryID = currentMemento.getString(TAG_FACTORY_ID);
            if (factoryID != null) {
                IElementFactory factory = PlatformUI.getWorkbench()
                        .getElementFactory(factoryID);
                if (factory != null) {
