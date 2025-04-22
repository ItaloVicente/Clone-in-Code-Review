		}

		Map activityRequirementBindingDefinitionsByActivityId = ActivityRequirementBindingDefinition
				.activityRequirementBindingDefinitionsByActivityId(activityRegistry
						.getActivityRequirementBindingDefinitions());
		Map<String, Set> activityRequirementBindingsByActivityId = new HashMap<String, Set>();

		for (Iterator iterator = activityRequirementBindingDefinitionsByActivityId.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String parentActivityId = (String) entry.getKey();

			if (activityDefinitionsById.containsKey(parentActivityId)) {
				Collection activityRequirementBindingDefinitions = (Collection) entry.getValue();

				if (activityRequirementBindingDefinitions != null) {
					for (Iterator iterator2 = activityRequirementBindingDefinitions.iterator(); iterator2
							.hasNext();) {
						ActivityRequirementBindingDefinition activityRequirementBindingDefinition = (ActivityRequirementBindingDefinition) iterator2
								.next();
						String childActivityId = activityRequirementBindingDefinition
								.getRequiredActivityId();

						if (activityDefinitionsById.containsKey(childActivityId)) {
							IActivityRequirementBinding activityRequirementBinding = new ActivityRequirementBinding(
									childActivityId, parentActivityId);
							Set<IActivityRequirementBinding> activityRequirementBindings = activityRequirementBindingsByActivityId
									.get(parentActivityId);

							if (activityRequirementBindings == null) {
								activityRequirementBindings = new HashSet<IActivityRequirementBinding>();
								activityRequirementBindingsByActivityId.put(parentActivityId,
										activityRequirementBindings);
							}

							activityRequirementBindings.add(activityRequirementBinding);
						}
					}
