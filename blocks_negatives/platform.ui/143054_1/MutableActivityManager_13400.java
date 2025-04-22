			enabledChanged = activity.setEnabled(enabledActivityIds
					.contains(activity.getId()));
		}

        boolean nameChanged = activity
                .setName(activityDefinition != null ? activityDefinition
                        .getName() : null);
        boolean descriptionChanged = activity
                .setDescription(activityDefinition != null ? activityDefinition
                        .getDescription() : null);
        boolean defaultEnabledChanged = activity.setDefaultEnabled(activityRegistry
                .getDefaultEnabledActivities().contains(activity.getId()));
        if (activityRequirementBindingsChanged
                || activityPatternBindingsChanged || definedChanged
                || enabledChanged || nameChanged || descriptionChanged
                || defaultEnabledChanged) {
			return new ActivityEvent(activity,
                    activityRequirementBindingsChanged,
                    activityPatternBindingsChanged, definedChanged,
                    descriptionChanged, enabledChanged, nameChanged, defaultEnabledChanged);
		}

        return null;
    }

    private Map updateCategories(Collection categoryIds) {
        Map categoryEventsByCategoryId = new TreeMap();

        for (Iterator iterator = categoryIds.iterator(); iterator.hasNext();) {
            String categoryId = (String) iterator.next();
            Category category = (Category) categoriesById.get(categoryId);

            if (category != null) {
                CategoryEvent categoryEvent = updateCategory(category);

                if (categoryEvent != null) {
