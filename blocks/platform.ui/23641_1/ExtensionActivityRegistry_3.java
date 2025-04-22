
		boolean activityRegistryChanged = false;

		if (!activityRequirementBindingDefinitions
				.equals(super.activityRequirementBindingDefinitions)) {
			super.activityRequirementBindingDefinitions = Collections
					.unmodifiableList(new ArrayList<ActivityRequirementBindingDefinition>(
							activityRequirementBindingDefinitions));
			activityRegistryChanged = true;
		}

		if (!activityDefinitions.equals(super.activityDefinitions)) {
			super.activityDefinitions = Collections.unmodifiableList(new ArrayList<ActivityDefinition>(
					activityDefinitions));
			activityRegistryChanged = true;
		}

		if (!activityPatternBindingDefinitions.equals(super.activityPatternBindingDefinitions)) {
			super.activityPatternBindingDefinitions = Collections.unmodifiableList(new ArrayList<ActivityPatternBindingDefinition>(
					activityPatternBindingDefinitions));
			activityRegistryChanged = true;
		}

		if (!categoryActivityBindingDefinitions.equals(super.categoryActivityBindingDefinitions)) {
			super.categoryActivityBindingDefinitions = Collections.unmodifiableList(new ArrayList<CategoryActivityBindingDefinition>(
					categoryActivityBindingDefinitions));
			activityRegistryChanged = true;
		}

		if (!categoryDefinitions.equals(super.categoryDefinitions)) {
			super.categoryDefinitions = Collections.unmodifiableList(new ArrayList<CategoryDefinition>(
					categoryDefinitions));
			activityRegistryChanged = true;
		}

		if (!defaultEnabledActivities.equals(super.defaultEnabledActivities)) {
			super.defaultEnabledActivities = Collections.unmodifiableList(new ArrayList<String>(
					defaultEnabledActivities));
			activityRegistryChanged = true;
		}

		if (activityRegistryChanged) {
