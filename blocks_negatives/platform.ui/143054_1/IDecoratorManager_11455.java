    /**
     * Fire a LabelProviderChangedEvent for the decorator that corresponds to
     * decoratorID if it exists and is enabled using the IBaseLabelProvider
     * as the argument to the event. Otherwise do nothing.
     * <p> This method must be called from the user interface thread as widget
     * updates may result. </p>
     *
     * @param decoratorId the decorator id
     */
    void update(String decoratorId);
