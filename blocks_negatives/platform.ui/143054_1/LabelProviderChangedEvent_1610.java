    /**
     * Creates a new event for the given source, indicating that the label
     * provided by the source for the given elements is no longer valid and should be updated.
     *
     * @param source the label provider
     * @param elements the element whose labels have changed
     */
    public LabelProviderChangedEvent(IBaseLabelProvider source,
            Object[] elements) {
        super(source);
        this.elements = elements;
    }
