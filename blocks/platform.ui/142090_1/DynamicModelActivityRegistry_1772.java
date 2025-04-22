	public void removeCategoryActivityBinding(String activityId,
			String categoryId) {
		categoryActivityBindingDefinitions
				.remove(new CategoryActivityBindingDefinition(activityId,
						categoryId, sourceId));
		fireActivityRegistryChanged();
	}
