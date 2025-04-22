		for (Iterator i = definedActivities.iterator(); i.hasNext();) {
			currentActivityId = (String) i.next();
			activityRequirementBindings = manager
					.getActivity(currentActivityId)
					.getActivityRequirementBindings();
			for (Iterator j = activityRequirementBindings.iterator(); j
					.hasNext();) {
				currentActivityRequirementBinding = (IActivityRequirementBinding) j
						.next();
				if (currentActivityRequirementBinding.getRequiredActivityId()
						.equals(activityId)) {
