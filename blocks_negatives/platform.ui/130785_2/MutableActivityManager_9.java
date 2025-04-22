                        CategoryActivityBindingDefinition categoryActivityBindingDefinition = (CategoryActivityBindingDefinition) iterator2
                                .next();
                        String activityId = categoryActivityBindingDefinition
                                .getActivityId();

                        if (activityDefinitionsById.containsKey(activityId)) {
                            ICategoryActivityBinding categoryActivityBinding = new CategoryActivityBinding(
                                    activityId, categoryId);
                            Set categoryActivityBindings = (Set) categoryActivityBindingsByCategoryId
                                    .get(categoryId);

                            if (categoryActivityBindings == null) {
                                categoryActivityBindings = new HashSet();
                                categoryActivityBindingsByCategoryId.put(
                                        categoryId, categoryActivityBindings);
                            }
