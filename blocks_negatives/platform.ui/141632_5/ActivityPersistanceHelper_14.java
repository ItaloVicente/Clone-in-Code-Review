	private final IActivityManagerListener activityManagerListener = new IActivityManagerListener() {

		@Override
		public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
			if (activityManagerEvent.haveDefinedActivityIdsChanged()) {
				Set<String> delta = new HashSet<>(activityManagerEvent.getActivityManager().getDefinedActivityIds());
				delta.removeAll(activityManagerEvent.getPreviouslyDefinedActivityIds());
				loadEnabledStates(activityManagerEvent.getActivityManager().getEnabledActivityIds(), delta);
			}
			if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
				saveEnabledStates();
			}
