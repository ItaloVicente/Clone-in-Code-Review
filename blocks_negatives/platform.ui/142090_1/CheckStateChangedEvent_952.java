    /**
     * Creates a new event for the given source, element, and checked state.
     *
     * @param source the source
     * @param element the element
     * @param state the checked state
     */
    public CheckStateChangedEvent(ICheckable source, Object element,
            boolean state) {
        super(source);
        this.element = element;
        this.state = state;
    }
