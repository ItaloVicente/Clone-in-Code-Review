	public void setStatusLineManager(IStatusLineManager manager) {
		statusLineManager = manager;
	}

	void showCategories() {
		isShowingCategories = true;
		refresh();
	}

	void showExpert() {
		isShowingExpertProperties = true;
		refresh();
	}

	private void updateCategories() {
		if (categories == null) {
			categories = new PropertySheetCategory[0];
		}

		List childEntries = getFilteredEntries(rootEntry.getChildEntries());

		if (childEntries.size() == 0) {
