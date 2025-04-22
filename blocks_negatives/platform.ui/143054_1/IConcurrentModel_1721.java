    /**
     * Requests that the receiver to call the given listener's setContents(...)
     * method at its earliest convenience. The receiver is allowed to compute the
     * elements asynchronously. That is, it can compute the result in a background
     * thread and call setContents(...) once the result is ready. If the result is
     * too large to return in one batch, it can call setContents with an empty array
     * followed by a sequence of adds.
     * <p>
     * Has no effect if an update is already queued for an identical listener.
     * </p>
     *
     * @param listener listener whose setContents method should be called. The
     * listener must have been previously registered with addListener.
     */
    public void requestUpdate(IConcurrentModelListener listener);
