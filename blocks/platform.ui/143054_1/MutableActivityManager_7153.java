		}
	}

	private void readRegistry(boolean setDefaults) {
		clearExpressions();
		Collection<ActivityDefinition> activityDefinitions = new ArrayList<>();
		activityDefinitions.addAll(activityRegistry.getActivityDefinitions());
		Map<String, ActivityDefinition> activityDefinitionsById = new HashMap<>(
				ActivityDefinition.activityDefinitionsById(activityDefinitions, false));

		for (Iterator<ActivityDefinition> iterator = activityDefinitionsById.values().iterator(); iterator.hasNext();) {
			ActivityDefinition activityDefinition = iterator.next();
			String name = activityDefinition.getName();

			if (name == null || name.length() == 0) {
