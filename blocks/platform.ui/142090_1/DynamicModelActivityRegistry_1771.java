	public void addCategoryActivityBinding(String activityId, String categoryId) {
		categoryActivityBindingDefinitions
				.add(new CategoryActivityBindingDefinition(activityId,
						categoryId, sourceId));
		fireActivityRegistryChanged();
	}
