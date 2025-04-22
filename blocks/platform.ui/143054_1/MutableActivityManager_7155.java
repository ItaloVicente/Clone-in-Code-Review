			}
		}

		Map<String, Collection<ActivityPatternBindingDefinition>> activityPatternBindingDefinitionsByActivityId = ActivityPatternBindingDefinition
				.activityPatternBindingDefinitionsByActivityId(activityRegistry.getActivityPatternBindingDefinitions());
		Map<String, Set<IActivityPatternBinding>> activityPatternBindingsByActivityId = new HashMap<>();

		for (Iterator<Entry<String, Collection<ActivityPatternBindingDefinition>>> iterator = activityPatternBindingDefinitionsByActivityId
				.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Collection<ActivityPatternBindingDefinition>> entry = iterator.next();
			String activityId = entry.getKey();

			if (activityDefinitionsById.containsKey(activityId)) {
				Collection<ActivityPatternBindingDefinition> activityPatternBindingDefinitions = entry.getValue();

				if (activityPatternBindingDefinitions != null) {
					for (Iterator<ActivityPatternBindingDefinition> iterator2 = activityPatternBindingDefinitions
							.iterator(); iterator2.hasNext();) {
						ActivityPatternBindingDefinition activityPatternBindingDefinition = iterator2.next();
						String pattern = activityPatternBindingDefinition.getPattern();

						if (pattern != null && pattern.length() != 0) {
							IActivityPatternBinding activityPatternBinding = new ActivityPatternBinding(activityId,
									pattern, activityPatternBindingDefinition.isEqualityPattern());
							Set<IActivityPatternBinding> activityPatternBindings = activityPatternBindingsByActivityId
									.get(activityId);

							if (activityPatternBindings == null) {
								activityPatternBindings = new HashSet<>();
								activityPatternBindingsByActivityId.put(activityId, activityPatternBindings);
							}

							activityPatternBindings.add(activityPatternBinding);
						}
					}
