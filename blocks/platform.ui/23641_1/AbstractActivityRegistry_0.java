				activityRegistryListeners.get(i).activityRegistryChanged(activityRegistryEvent);
			}
		}
	}

	@Override
	public List<ActivityRequirementBindingDefinition> getActivityRequirementBindingDefinitions() {
		return activityRequirementBindingDefinitions;
	}

	@Override
	public List<ActivityDefinition> getActivityDefinitions() {
		return activityDefinitions;
	}

	@Override
	public List<ActivityPatternBindingDefinition> getActivityPatternBindingDefinitions() {
		return activityPatternBindingDefinitions;
	}

	@Override
	public List<CategoryActivityBindingDefinition> getCategoryActivityBindingDefinitions() {
		return categoryActivityBindingDefinitions;
	}

	@Override
	public List<CategoryDefinition> getCategoryDefinitions() {
		return categoryDefinitions;
	}

	@Override
	public void removeActivityRegistryListener(IActivityRegistryListener activityRegistryListener) {
		if (activityRegistryListener == null) {
