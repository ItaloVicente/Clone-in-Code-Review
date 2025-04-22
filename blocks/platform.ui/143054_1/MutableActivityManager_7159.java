	private ActivityEvent updateActivity(Activity activity) {
		Set<IActivityRequirementBinding> activityRequirementBindings = activityRequirementBindingsByActivityId
				.get(activity.getId());
		boolean activityRequirementBindingsChanged = activity.setActivityRequirementBindings(
				activityRequirementBindings != null ? activityRequirementBindings : Collections.emptySet());
		Set<IActivityPatternBinding> activityPatternBindings = activityPatternBindingsByActivityId
				.get(activity.getId());
		boolean activityPatternBindingsChanged = activity.setActivityPatternBindings(
				activityPatternBindings != null ? activityPatternBindings : Collections.emptySet());
		ActivityDefinition activityDefinition = activityDefinitionsById.get(activity.getId());
		boolean definedChanged = activity.setDefined(activityDefinition != null);

		IEvaluationReference ref = refsByActivityDefinition.get(activityDefinition);
		IEvaluationService evaluationService = PlatformUI.getWorkbench().getService(IEvaluationService.class);
