    /**
     * Attaches a listener that will receive notifications when any
     * of the given properties change. If an identical listener is already registered,
     * then this will add additional IDs to the set of properties being monitored
     * by the given listener.
     *
     * @param listener
     * @param propertyIds
     * @since 3.1
     */
    public void addListener(String[] propertyIds, IPropertyMapListener listener);
