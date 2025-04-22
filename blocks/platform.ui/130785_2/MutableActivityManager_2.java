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
