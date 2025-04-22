	private void pruneEmptyCategories(WizardCollectionElement parent) {
		Object[] children = parent.getChildren(null);
		for (Object element : children) {
			WizardCollectionElement child = (WizardCollectionElement) element;
			pruneEmptyCategories(child);
			boolean shouldPrune = child.getId().equals(FULL_EXAMPLES_WIZARD_CATEGORY);
			if (child.isEmpty() && shouldPrune) {
