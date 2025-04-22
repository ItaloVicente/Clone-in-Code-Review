		boolean addMisc = false;

		for (int i = 0; i < childEntries.size(); i++) {
			IPropertySheetEntry childEntry = (IPropertySheetEntry) childEntries
					.get(i);
			String categoryName = childEntry.getCategory();
			if (categoryName == null) {
				misc.addEntry(childEntry);
				addMisc = true;
				categoriesToRemove.remove(misc);
			} else {
				PropertySheetCategory category = (PropertySheetCategory) categoryCache
						.get(categoryName);
				if (category == null) {
					category = new PropertySheetCategory(categoryName);
					categoryCache.put(categoryName, category);
				} else {
					categoriesToRemove.remove(category);
				}
				category.addEntry(childEntry);
			}
		}

		if (addMisc) {
