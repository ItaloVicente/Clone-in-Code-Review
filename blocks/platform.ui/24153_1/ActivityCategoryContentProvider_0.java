		Set<String> activityIds = InternalActivityHelper.getActivityIdsForCategory(manager,
				category);
		List<CategorizedActivity> categoryActivities = new ArrayList<CategorizedActivity>(
				activityIds.size());
		for (Iterator<String> i = activityIds.iterator(); i.hasNext();) {
			String activityId = i.next();
