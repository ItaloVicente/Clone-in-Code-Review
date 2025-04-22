    /**
     * Prefix for all activity preferences
     */

    /**
     * Singleton instance.
     */
    private static ActivityPersistanceHelper singleton;

    /**
     * The listener that responds to changes in the <code>IActivityManager</code>
     */
    private final IActivityManagerListener activityManagerListener = new IActivityManagerListener() {

        @Override
		public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {
