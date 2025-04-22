			}
		}

		Map<String, Collection<CategoryActivityBindingDefinition>> categoryActivityBindingDefinitionsByCategoryId = CategoryActivityBindingDefinition
				.categoryActivityBindingDefinitionsByCategoryId(
						activityRegistry.getCategoryActivityBindingDefinitions());
		Map<String, Set<ICategoryActivityBinding>> categoryActivityBindingsByCategoryId = new HashMap<>();

		for (Iterator<Entry<String, Collection<CategoryActivityBindingDefinition>>> iterator = categoryActivityBindingDefinitionsByCategoryId
				.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Collection<CategoryActivityBindingDefinition>> entry = iterator.next();
			String categoryId = entry.getKey();

			if (categoryDefinitionsById.containsKey(categoryId)) {
				Collection<CategoryActivityBindingDefinition> categoryActivityBindingDefinitions = entry.getValue();

				if (categoryActivityBindingDefinitions != null) {
					for (Iterator<CategoryActivityBindingDefinition> iterator2 = categoryActivityBindingDefinitions
							.iterator(); iterator2.hasNext();) {
						CategoryActivityBindingDefinition categoryActivityBindingDefinition = iterator2.next();
						String activityId = categoryActivityBindingDefinition.getActivityId();

						if (activityDefinitionsById.containsKey(activityId)) {
							ICategoryActivityBinding categoryActivityBinding = new CategoryActivityBinding(activityId,
									categoryId);
							Set<ICategoryActivityBinding> categoryActivityBindings = categoryActivityBindingsByCategoryId
									.get(categoryId);

							if (categoryActivityBindings == null) {
								categoryActivityBindings = new HashSet<>();
								categoryActivityBindingsByCategoryId.put(categoryId, categoryActivityBindings);
							}

							categoryActivityBindings.add(categoryActivityBinding);
						}
					}
