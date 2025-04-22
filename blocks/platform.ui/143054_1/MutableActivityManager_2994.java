	synchronized public void setEnabledActivityIds(Set<String> enabledActivityIds) {
		enabledActivityIds = new HashSet<>(enabledActivityIds);
		Set<String> requiredActivityIds = new HashSet<>(enabledActivityIds);
		getRequiredActivityIds(enabledActivityIds, requiredActivityIds);
		enabledActivityIds = requiredActivityIds;
		Set<String> deltaActivityIds = null;
		boolean activityManagerChanged = false;
		Map<String, ActivityEvent> activityEventsByActivityId = null;

		Set<String> previouslyEnabledActivityIds = null;
		if (!this.enabledActivityIds.equals(enabledActivityIds)) {
			previouslyEnabledActivityIds = this.enabledActivityIds;
			activityManagerChanged = true;

			Set<String> additions = new HashSet<>(enabledActivityIds);
			additions.removeAll(previouslyEnabledActivityIds);

			Set<String> removals = new HashSet<>(previouslyEnabledActivityIds);
			removals.removeAll(enabledActivityIds);

			removeExpressionControlledActivities(additions);
			removeExpressionControlledActivities(removals);

