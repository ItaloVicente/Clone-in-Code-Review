    /**
     * Registers an instance of <code>IActivityManagerListener</code> to
     * listen for changes to properties of this instance.
     *
     * @param activityManagerListener
     *            the instance to register. Must not be <code>null</code>.
     *            If an attempt is made to register an instance which is
     *            already registered with this instance, no operation is
     *            performed.
     */
    void addActivityManagerListener(
            IActivityManagerListener activityManagerListener);
