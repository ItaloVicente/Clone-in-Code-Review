		return children;
	}

	private List getChildren(IPropertySheetEntry entry) {
		if (entry == rootEntry && isShowingCategories) {
			if (categories.length > 1
					|| (categories.length == 1 && !categories[0]
							.getCategoryName().equals(
									MISCELLANEOUS_CATEGORY_NAME))) {
