    /**
     * Removes a listener from this model. The receiver will stop sending
     * notifications to the given listener as soon as possible (although
     * some additional notifications may still if arrive if the receiver
     * was in the process of sending notifications in another thread).
     * Any pending updates for this listener will be cancelled.
     * <p>
     * Has no effect if the given listener is not known to this model.
     * </p>
     *
     * @param listener listener to remove
     */
    public void removeListener(IConcurrentModelListener listener);
