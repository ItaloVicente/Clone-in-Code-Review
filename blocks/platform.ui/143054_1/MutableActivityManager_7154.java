		}

		Map<String, Collection<ActivityRequirementBindingDefinition>> activityRequirementBindingDefinitionsByActivityId = ActivityRequirementBindingDefinition
				.activityRequirementBindingDefinitionsByActivityId(
						activityRegistry.getActivityRequirementBindingDefinitions());
		Map<String, Set<IActivityRequirementBinding>> activityRequirementBindingsByActivityId = new HashMap<>();

		for (Iterator<Entry<String, Collection<ActivityRequirementBindingDefinition>>> iterator = activityRequirementBindingDefinitionsByActivityId
				.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Collection<ActivityRequirementBindingDefinition>> entry = iterator.next();
			String parentActivityId = entry.getKey();

			if (activityDefinitionsById.containsKey(parentActivityId)) {
				Collection<ActivityRequirementBindingDefinition> activityRequirementBindingDefinitions = entry
						.getValue();

				if (activityRequirementBindingDefinitions != null) {
					for (Iterator<ActivityRequirementBindingDefinition> iterator2 = activityRequirementBindingDefinitions
							.iterator(); iterator2.hasNext();) {
						ActivityRequirementBindingDefinition activityRequirementBindingDefinition = iterator2.next();
						String childActivityId = activityRequirementBindingDefinition.getRequiredActivityId();

						if (activityDefinitionsById.containsKey(childActivityId)) {
							IActivityRequirementBinding activityRequirementBinding = new ActivityRequirementBinding(
									childActivityId, parentActivityId);
							Set<IActivityRequirementBinding> activityRequirementBindings = activityRequirementBindingsByActivityId
									.get(parentActivityId);

							if (activityRequirementBindings == null) {
								activityRequirementBindings = new HashSet<>();
								activityRequirementBindingsByActivityId.put(parentActivityId,
										activityRequirementBindings);
							}

							activityRequirementBindings.add(activityRequirementBinding);
						}
					}
