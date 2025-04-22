	private void populateActivityRequirementBindingDefinitions() {
		activityRequirementBindingDefinitions
				.add(new ActivityRequirementBindingDefinition(
						((ActivityDefinition) activityDefinitions.toArray()[0])
								.getId(),
						((ActivityDefinition) activityDefinitions.toArray()[1])
								.getId(), sourceId)); //$NON-NLS-1$
		activityRequirementBindingDefinitions
				.add(new ActivityRequirementBindingDefinition(
						((ActivityDefinition) activityDefinitions.toArray()[2])
								.getId(),
						((ActivityDefinition) activityDefinitions.toArray()[3])
								.getId(), sourceId)); //$NON-NLS-1$
	}
