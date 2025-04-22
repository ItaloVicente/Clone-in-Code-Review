		enabledActivityIds = new HashSet<String>(enabledActivityIds);
		Set<String> requiredActivityIds = new HashSet<String>(enabledActivityIds);
		getRequiredActivityIds(enabledActivityIds, requiredActivityIds);
		enabledActivityIds = requiredActivityIds;
		Set<String> deltaActivityIds = null;
		boolean activityManagerChanged = false;
		Map<String, ActivityEvent> activityEventsByActivityId = null;

		Set<String> previouslyEnabledActivityIds = null;
		if (!this.enabledActivityIds.equals(enabledActivityIds)) {
			previouslyEnabledActivityIds = this.enabledActivityIds;
			activityManagerChanged = true;

			Set<String> additions = new HashSet<String>(enabledActivityIds);
			additions.removeAll(previouslyEnabledActivityIds);

			Set<String> removals = new HashSet<String>(previouslyEnabledActivityIds);
			removals.removeAll(enabledActivityIds);

			removeExpressionControlledActivities(additions);
			removeExpressionControlledActivities(removals);

