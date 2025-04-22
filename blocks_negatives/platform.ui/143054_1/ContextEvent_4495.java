    /**
     * Creates a new instance of this class.
     *
     * @param context
     *            the instance of the interface that changed.
     * @param definedChanged
     *            true, iff the defined property changed.
     * @param enabledChanged
     *            true, iff the enabled property changed.
     * @param nameChanged
     *            true, iff the name property changed.
     * @param parentIdChanged
     *            true, iff the parentId property changed.
     */
    public ContextEvent(IContext context, boolean definedChanged,
            boolean enabledChanged, boolean nameChanged, boolean parentIdChanged) {
        if (context == null) {
