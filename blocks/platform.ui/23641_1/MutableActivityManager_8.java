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
			Set<String> childActivityIds = new HashSet<String>();
			Set activityRequirementBindings = activity.getActivityRequirementBindings();

			for (Iterator iterator2 = activityRequirementBindings.iterator(); iterator2.hasNext();) {
				IActivityRequirementBinding activityRequirementBinding = (IActivityRequirementBinding) iterator2
						.next();
				childActivityIds.add(activityRequirementBinding.getRequiredActivityId());
			}

			childActivityIds.removeAll(requiredActivityIds);
			requiredActivityIds.addAll(childActivityIds);
			getRequiredActivityIds(childActivityIds, requiredActivityIds);
		}
	}

	private void notifyActivities(Map<String, ActivityEvent> activityEventsByActivityId) {
		for (Iterator iterator = activityEventsByActivityId.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String activityId = (String) entry.getKey();
			ActivityEvent activityEvent = (ActivityEvent) entry.getValue();
			Activity activity = activitiesById.get(activityId);

			if (activity != null) {
