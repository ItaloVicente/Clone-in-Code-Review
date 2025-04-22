        }
    }

    private void readRegistry(boolean setDefaults) {
    	clearExpressions();
        Collection activityDefinitions = new ArrayList();
        activityDefinitions.addAll(activityRegistry.getActivityDefinitions());
        Map activityDefinitionsById = new HashMap(ActivityDefinition
                .activityDefinitionsById(activityDefinitions, false));

        for (Iterator iterator = activityDefinitionsById.values().iterator(); iterator
                .hasNext();) {
            ActivityDefinition activityDefinition = (ActivityDefinition) iterator
                    .next();
            String name = activityDefinition.getName();

            if (name == null || name.length() == 0) {
