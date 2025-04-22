            }
        }

        Map categoryActivityBindingDefinitionsByCategoryId = CategoryActivityBindingDefinition
                .categoryActivityBindingDefinitionsByCategoryId(activityRegistry
                        .getCategoryActivityBindingDefinitions());
        Map categoryActivityBindingsByCategoryId = new HashMap();

        for (Iterator iterator = categoryActivityBindingDefinitionsByCategoryId
                .entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String categoryId = (String) entry.getKey();

            if (categoryDefinitionsById.containsKey(categoryId)) {
                Collection categoryActivityBindingDefinitions = (Collection) entry
                        .getValue();

                if (categoryActivityBindingDefinitions != null) {
					for (Iterator iterator2 = categoryActivityBindingDefinitions
                            .iterator(); iterator2.hasNext();) {
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

                            categoryActivityBindings
                                    .add(categoryActivityBinding);
                        }
                    }
