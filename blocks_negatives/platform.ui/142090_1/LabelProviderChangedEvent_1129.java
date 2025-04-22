    /**
     * Creates a new event for the given source, indicating that the label
     * provided by the source for the given element is no longer valid and should be updated.
     *
     * @param source the label provider
     * @param element the element whose label needs to be updated
     */
    public LabelProviderChangedEvent(IBaseLabelProvider source, Object element) {
        super(source);
        this.elements = new Object[1];
        this.elements[0] = element;
    }
