	public void addActivityRequirementBinding(String childId, String parentId) {
		activityRequirementBindingDefinitions
				.add(new ActivityRequirementBindingDefinition(childId,
						parentId, sourceId));
		fireActivityRegistryChanged();
	}
