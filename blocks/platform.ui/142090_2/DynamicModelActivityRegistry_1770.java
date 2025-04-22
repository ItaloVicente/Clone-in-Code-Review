	public void removeActivityRequirementBinding(String childId, String parentId) {
		activityRequirementBindingDefinitions
				.remove(new ActivityRequirementBindingDefinition(childId,
						parentId, sourceId));
		fireActivityRegistryChanged();
	}
