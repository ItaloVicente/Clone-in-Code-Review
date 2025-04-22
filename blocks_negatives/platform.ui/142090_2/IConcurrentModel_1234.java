    /**
     * Adds a listener to this model. The listener should be given the model's
     * current contents (either through setContents or a sequence of adds) at the
     * receiver's earliest convenience. The receiver will notify the listener
     * about any changes in state until the listener is removed.
     *
     * <p>
     * Has no effect if an identical listener is already registered.
     * </p>
     *
     * @param listener listener to add
     */
    public void addListener(IConcurrentModelListener listener);
