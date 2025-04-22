    /**
     * Creates a new instance of this class.
     *
     * @param category
     *        the instance of the interface that changed.
     * @param categoryActivityBindingsChanged
     *        <code>true</code>, iff the categoryActivityBindings property changed.
     * @param definedChanged
     *        <code>true</code>, iff the defined property changed.
     * @param descriptionChanged
     *        <code>true</code>, iff the description property changed.
     * @param nameChanged
     *        <code>true</code>, iff the name property changed.
     */
    public CategoryEvent(ICategory category,
            boolean categoryActivityBindingsChanged, boolean definedChanged,
            boolean descriptionChanged, boolean nameChanged) {
        if (category == null) {
