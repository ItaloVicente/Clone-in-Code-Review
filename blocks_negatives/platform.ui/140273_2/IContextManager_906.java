    /**
     * Unregisters an instance of <code>IContextManagerListener</code>
     * listening for changes to properties of this instance.
     *
     * @param contextManagerListener
     *            the instance to unregister. Must not be <code>null</code>.
     *            If an attempt is made to unregister an instance which is not
     *            already registered with this instance, no operation is
     *            performed.
     */
    void removeContextManagerListener(
            IContextManagerListener contextManagerListener);
