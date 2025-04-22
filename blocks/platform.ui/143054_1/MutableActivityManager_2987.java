		}
	}

	private void notifyCategories(Map<String, CategoryEvent> categoryEventsByCategoryId) {
		for (Iterator<Entry<String, CategoryEvent>> iterator = categoryEventsByCategoryId.entrySet()
				.iterator(); iterator.hasNext();) {
			Entry<String, CategoryEvent> entry = iterator.next();
			String categoryId = entry.getKey();
			CategoryEvent categoryEvent = entry.getValue();
			Category category = categoriesById.get(categoryId);

			if (category != null) {
