			enabledChanged = activity.setEnabled(enabledActivityIds.contains(activity.getId()));
		}

		boolean nameChanged = activity.setName(activityDefinition != null ? activityDefinition
				.getName() : null);
		boolean descriptionChanged = activity
				.setDescription(activityDefinition != null ? activityDefinition.getDescription()
						: null);
		boolean defaultEnabledChanged = activity.setDefaultEnabled(activityRegistry
				.getDefaultEnabledActivities().contains(activity.getId()));
		if (activityRequirementBindingsChanged || activityPatternBindingsChanged || definedChanged
				|| enabledChanged || nameChanged || descriptionChanged || defaultEnabledChanged) {
			return new ActivityEvent(activity, activityRequirementBindingsChanged,
					activityPatternBindingsChanged, definedChanged, descriptionChanged,
					enabledChanged, nameChanged, defaultEnabledChanged);
		}

		return null;
	}

	private Map<String, CategoryEvent> updateCategories(Collection<String> categoryIds) {
		Map<String, CategoryEvent> categoryEventsByCategoryId = new TreeMap<String, CategoryEvent>();

		for (Iterator<String> iterator = categoryIds.iterator(); iterator.hasNext();) {
			String categoryId = iterator.next();
			Category category = categoriesById.get(categoryId);

			if (category != null) {
				CategoryEvent categoryEvent = updateCategory(category);

				if (categoryEvent != null) {
