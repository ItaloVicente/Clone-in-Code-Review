    private ActivityEvent updateActivity(Activity activity) {
        Set activityRequirementBindings = (Set) activityRequirementBindingsByActivityId
                .get(activity.getId());
        boolean activityRequirementBindingsChanged = activity
                .setActivityRequirementBindings(activityRequirementBindings != null ? activityRequirementBindings
                        : Collections.EMPTY_SET);
        Set activityPatternBindings = (Set) activityPatternBindingsByActivityId
                .get(activity.getId());
        boolean activityPatternBindingsChanged = activity
                .setActivityPatternBindings(activityPatternBindings != null ? activityPatternBindings
                        : Collections.EMPTY_SET);
        ActivityDefinition activityDefinition = (ActivityDefinition) activityDefinitionsById
                .get(activity.getId());
        boolean definedChanged = activity
                .setDefined(activityDefinition != null);

        IEvaluationReference ref = (IEvaluationReference) refsByActivityDefinition
				.get(activityDefinition);
		IEvaluationService evaluationService = PlatformUI
				.getWorkbench().getService(IEvaluationService.class);
