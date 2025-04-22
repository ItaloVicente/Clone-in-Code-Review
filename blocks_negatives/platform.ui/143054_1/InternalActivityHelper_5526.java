	public static Set getPartiallyEnabledCategories(
			IActivityManager activityManager) {
		Set definedCategoryIds = activityManager.getDefinedCategoryIds();
		Set partialCategories = new HashSet();
		for (Iterator i = definedCategoryIds.iterator(); i.hasNext();) {
			String categoryId = (String) i.next();
