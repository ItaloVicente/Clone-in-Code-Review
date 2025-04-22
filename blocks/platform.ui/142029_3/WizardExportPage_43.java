		if (newlySelectedResources != null) {
			selectedResources = Arrays.asList(newlySelectedResources);
			displayResourcesSelectedCount(selectedResources.size());
		}
	}

	protected void handleTypesEditButtonPressed() {
		Object[] newSelectedTypes = queryResourceTypesToExport();

		if (newSelectedTypes != null) { // ie.- did not press Cancel
			List result = new ArrayList(newSelectedTypes.length);
			for (Object newSelectedType : newSelectedTypes) {
