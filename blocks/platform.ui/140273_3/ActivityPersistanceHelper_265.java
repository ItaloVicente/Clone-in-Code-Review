	protected static final String PREFIX = "UIActivities."; //$NON-NLS-1$

	private static ActivityPersistanceHelper singleton;

	private final IActivityManagerListener activityManagerListener = new IActivityManagerListener() {

		@Override
		public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
