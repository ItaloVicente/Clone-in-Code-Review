
	private ActivityEvent updateActivity(Activity activity) {
		Set activityRequirementBindings = activityRequirementBindingsByActivityId.get(activity
				.getId());
		boolean activityRequirementBindingsChanged = activity
				.setActivityRequirementBindings(activityRequirementBindings != null ? activityRequirementBindings
						: Collections.EMPTY_SET);
		Set activityPatternBindings = activityPatternBindingsByActivityId.get(activity.getId());
		boolean activityPatternBindingsChanged = activity
				.setActivityPatternBindings(activityPatternBindings != null ? activityPatternBindings
						: Collections.EMPTY_SET);
		ActivityDefinition activityDefinition = (ActivityDefinition) activityDefinitionsById
				.get(activity.getId());
		boolean definedChanged = activity.setDefined(activityDefinition != null);

		IEvaluationReference ref = refsByActivityDefinition.get(activityDefinition);
		IEvaluationService evaluationService = (IEvaluationService) PlatformUI.getWorkbench()
				.getService(IEvaluationService.class);
