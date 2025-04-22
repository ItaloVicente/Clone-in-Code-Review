		}
	}

	private void readRegistry(boolean setDefaults) {
		if (!isRegexpSupported()) {
			return;
		}
		clearExpressions();
		Collection<ActivityDefinition> activityDefinitions = new ArrayList<ActivityDefinition>();
		activityDefinitions.addAll(activityRegistry.getActivityDefinitions());
		Map<String, ActivityDefinition> activityDefinitionsById = new HashMap<String, ActivityDefinition>(
				ActivityDefinition.activityDefinitionsById(activityDefinitions, false));

		for (Iterator<ActivityDefinition> iterator = activityDefinitionsById.values().iterator(); iterator
				.hasNext();) {
			ActivityDefinition activityDefinition = iterator.next();
			String name = activityDefinition.getName();

			if (name == null || name.length() == 0) {
