		Set<?> enabledCategories = getEnabledCategories(activityManager);
		for (Object name : enabledCategories) {
			String categoryId = (String) name;
			if (getActivityIdsForCategory(
					activityManager.getCategory(categoryId)).contains(
					activityId)) {
