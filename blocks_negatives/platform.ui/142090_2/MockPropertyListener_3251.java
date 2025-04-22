    private CallHistory callTrace;

    private Object sourceMask;

    private int sourceId;

    /**
     * @param source the event source that fires the event to this listener
     * @param id the property id for the event
     */
    public MockPropertyListener(Object source, int id) {
        sourceMask = source;
        sourceId = id;
        callTrace = new CallHistory(this);
    }

    /**
     * @see IPropertyListener#propertyChanged(Object, int)
     */
    @Override
