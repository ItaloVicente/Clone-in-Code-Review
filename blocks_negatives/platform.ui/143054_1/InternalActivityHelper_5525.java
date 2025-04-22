	public static Set getActivityIdsForCategory(
			IActivityManager activityManager, ICategory category) {
		Set bindings = category.getCategoryActivityBindings();
		Set activityIds = new HashSet();
		for (Iterator i = bindings.iterator(); i.hasNext();) {
			ICategoryActivityBinding binding = (ICategoryActivityBinding) i
					.next();
