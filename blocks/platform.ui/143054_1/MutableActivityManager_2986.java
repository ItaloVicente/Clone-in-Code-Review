		Identifier identifier = identifiersById.get(identifierId);

		if (identifier == null) {
			identifier = new Identifier(identifierId);
			updateIdentifier(identifier);
			identifiersById.put(identifierId, identifier);
		}

		return identifier;
	}

	private void getRequiredActivityIds(Set<String> activityIds, Set<String> requiredActivityIds) {
		for (Iterator<String> iterator = activityIds.iterator(); iterator.hasNext();) {
			String activityId = iterator.next();
			IActivity activity = getActivity(activityId);
			Set<String> childActivityIds = new HashSet<>();
			Set<IActivityRequirementBinding> activityRequirementBindings = activity.getActivityRequirementBindings();

			for (Iterator<IActivityRequirementBinding> iterator2 = activityRequirementBindings.iterator(); iterator2
					.hasNext();) {
				IActivityRequirementBinding activityRequirementBinding = iterator2.next();
				childActivityIds.add(activityRequirementBinding.getRequiredActivityId());
			}

			childActivityIds.removeAll(requiredActivityIds);
			requiredActivityIds.addAll(childActivityIds);
			getRequiredActivityIds(childActivityIds, requiredActivityIds);
		}
	}

	private void notifyActivities(Map<String, ActivityEvent> activityEventsByActivityId) {
		for (Iterator<Map.Entry<String, ActivityEvent>> iterator = activityEventsByActivityId.entrySet()
				.iterator(); iterator.hasNext();) {
			Entry<String, ActivityEvent> entry = iterator.next();
			String activityId = entry.getKey();
			ActivityEvent activityEvent = entry.getValue();
			Activity activity = activitiesById.get(activityId);

			if (activity != null) {
