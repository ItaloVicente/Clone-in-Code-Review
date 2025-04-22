	private void setEnabledStates(Set enabledActivities) {
		Set categories = activitySupport
				.getDefinedCategoryIds();
		List checked = new ArrayList(10), grayed = new ArrayList(10);
		for (Iterator i = categories.iterator(); i.hasNext();) {
			String categoryId = (String) i.next();
			ICategory category = activitySupport
					.getCategory(categoryId);
