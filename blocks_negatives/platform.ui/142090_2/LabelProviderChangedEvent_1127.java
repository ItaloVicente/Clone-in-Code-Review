    /**
     * Creates a new event for the given source, indicating that all labels
     * provided by the source are no longer valid and should be updated.
     *
     * @param source the label provider
     */
    public LabelProviderChangedEvent(IBaseLabelProvider source) {
        super(source);
    }
