		Set activityIds = InternalActivityHelper.getActivityIdsForCategory(
				manager, category);
		List categoryActivities = new ArrayList(activityIds.size());
		for (Iterator i = activityIds.iterator(); i.hasNext();) {
			String activityId = (String) i.next();
			categoryActivities.add(new CategorizedActivity(category, manager
					.getActivity(activityId)));
