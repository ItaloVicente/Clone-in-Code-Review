    /**
     * The map of previous attributes, if they changed.  If they did not change,
     * then this value is <code>null</code>.  The map's keys are the attribute
     * names (strings), and its value are any object.
     *
     * This is the original map passed into the constructor. This object always
     * returns a copy of this map, not the original. However the constructor of
     * this object is called very frequently and the map is rarely requested,
     * so we only copy the map the first time it is requested.
     *
     * @since 3.1
     */
    private final Map originalPreviousAttributeValuesByName;
