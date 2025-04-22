		CategoryNode[] flatArray = new CategoryNode[deferCategories.size()];
		for (int i = 0; i < deferCategories.size(); i++) {
			flatArray[i] = new CategoryNode((Category) deferCategories.get(i));
		}
		Collections.sort(Arrays.asList(flatArray), comparer);

		for (CategoryNode categoryNode : flatArray) {
			Category cat = categoryNode.getCategory();
			finishCategory(cat);
		}

		deferCategories = null;
	}

	private void finishCategory(Category category) {
		String[] categoryPath = category.getParentPath();
		WizardCollectionElement parent = wizardElements; // ie.- root

		if (categoryPath != null) {
			for (String parentPath : categoryPath) {
				WizardCollectionElement tempElement = getChildWithID(parent, parentPath);
				if (tempElement == null) {
					return;
				}
				parent = tempElement;
			}
		}

		Object test = getChildWithID(parent, category.getId());
		if (test != null) {
