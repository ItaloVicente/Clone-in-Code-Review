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
            if (activityManagerEvent.haveDefinedActivityIdsChanged()) {
                Set delta = new HashSet(activityManagerEvent
                        .getActivityManager().getDefinedActivityIds());
                delta.removeAll(activityManagerEvent
                        .getPreviouslyDefinedActivityIds());
                loadEnabledStates(activityManagerEvent
                        .getActivityManager().getEnabledActivityIds(), delta);
            }
            if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
				saveEnabledStates();
			}
        }
    };
