		Set<ICategoryActivityBinding> categoryActivityBindings = categoryActivityBindingsByCategoryId
				.get(category.getId());
		boolean categoryActivityBindingsChanged = category.setCategoryActivityBindings(
				categoryActivityBindings != null ? categoryActivityBindings : Collections.emptySet());
		CategoryDefinition categoryDefinition = categoryDefinitionsById.get(category.getId());
		boolean definedChanged = category.setDefined(categoryDefinition != null);
		boolean nameChanged = category.setName(categoryDefinition != null ? categoryDefinition.getName() : null);
		boolean descriptionChanged = category
				.setDescription(categoryDefinition != null ? categoryDefinition.getDescription() : null);

		if (categoryActivityBindingsChanged || definedChanged || nameChanged || descriptionChanged) {
			return new CategoryEvent(category, categoryActivityBindingsChanged, definedChanged, descriptionChanged,
					nameChanged);
