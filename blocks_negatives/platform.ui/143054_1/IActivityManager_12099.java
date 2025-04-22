    /**
     * Removes an instance of <code>IActivityManagerListener</code>
     * listening for changes to properties of this instance.
     *
     * @param activityManagerListener
     *            the instance to remove. Must not be <code>null</code>.
     *            If an attempt is made to remove an instance which is not
     *            already registered with this instance, no operation is
     *            performed.
     */
    void removeActivityManagerListener(
            IActivityManagerListener activityManagerListener);
