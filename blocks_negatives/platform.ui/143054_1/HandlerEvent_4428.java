    /**
     * Creates a new instance of this class.
     *
     * @param handler
     *            the instance of the interface that changed.
     * @param attributeValuesByNameChanged
     *            true, iff the attributeValuesByName property changed.
     * @param previousAttributeValuesByName
     *            the map of previous attribute values by name. This map may be
     *            empty. If this map is not empty, it's collection of keys must
     *            only contain instances of <code>String</code>. This map
     *            must be <code>null</code> if attributeValuesByNameChanged is
     *            <code>false</code> and must not be null if
     *            attributeValuesByNameChanged is <code>true</code>.
     */
