    /**
     * Adds a listener to this label provider.
     * Has no effect if an identical listener is already registered.
     * <p>
     * Label provider listeners are informed about state changes
     * that affect the rendering of the viewer that uses this label provider.
     * </p>
     *
     * @param listener a label provider listener
     */
    public void addListener(ILabelProviderListener listener);
