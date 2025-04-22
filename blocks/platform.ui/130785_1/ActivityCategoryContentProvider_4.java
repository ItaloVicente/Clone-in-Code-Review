		for (Iterator<String> i = definedActivities.iterator(); i.hasNext();) {
			currentActivityId = i.next();
			activityRequirementBindings = manager.getActivity(currentActivityId).getActivityRequirementBindings();
			for (Iterator<IActivityRequirementBinding> j = activityRequirementBindings.iterator(); j.hasNext();) {
				currentActivityRequirementBinding = j.next();
				if (currentActivityRequirementBinding.getRequiredActivityId().equals(activityId)) {
