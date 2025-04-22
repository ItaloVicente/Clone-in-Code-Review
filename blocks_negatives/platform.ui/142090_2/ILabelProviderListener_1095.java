    /**
     * Notifies this listener that the state of the label provider
     * has changed in a way that affects the labels it computes.
     * <p>
     * A typical response would be to refresh all labels by
     * re-requesting them from the label provider.
     * </p>
     *
     * @param event the label provider change event
     */
    public void labelProviderChanged(LabelProviderChangedEvent event);
