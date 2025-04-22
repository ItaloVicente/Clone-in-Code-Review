		MutableActivityManager clone = new MutableActivityManager(advisor, activityRegistry);
		clone.setEnabledActivityIds(getEnabledActivityIds());
		return clone;
	}

	private Job getUpdateJob() {
		if (deferredIdentifierJob == null) {
