    /**
     * Creates a new event for the given source and element.
     *
     * @param source the tree viewer
     * @param element the element
     */
    public TreeExpansionEvent(AbstractTreeViewer source, Object element) {
        super(source);
        this.element = element;
    }
