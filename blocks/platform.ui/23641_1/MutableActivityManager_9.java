		}
	}

	private void notifyCategories(Map<String, CategoryEvent> categoryEventsByCategoryId) {
		for (Iterator iterator = categoryEventsByCategoryId.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String categoryId = (String) entry.getKey();
			CategoryEvent categoryEvent = (CategoryEvent) entry.getValue();
			Category category = categoriesById.get(categoryId);

			if (category != null) {
